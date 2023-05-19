package com.example.icare.controller;

import com.example.icare.domain.Nutritionist;
import com.example.icare.domain.Patient;
import com.example.icare.domain.Report;
import com.example.icare.domain.Restaurant;
import com.example.icare.service.*;
import com.example.icare.user.User;
import com.example.icare.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/admin")
public class AdminController  {
    private final ReportService reportService;
    private final NutritionistService nutritionistService;
    private final RestaurantService restaurantService;
    private final PatientService patientService;
    private final UserService userService;

    @Autowired
    public AdminController( ReportService reportService, NutritionistService nutritionistService, RestaurantService restaurantService, PatientService patientService, UserService userService) {
        this.reportService = reportService;
        this.nutritionistService = nutritionistService;
        this.restaurantService = restaurantService;
        this.patientService = patientService;
        this.userService = userService;

    }

    //VIEW SIGN UP REQUEST
    @GetMapping("/signupRequest/centers") //view list of NON VERIFIED nutritionist(centers)
    public List<Nutritionist> getAllNonVerifiedCenters (){
        return nutritionistService.getAllCentersWithStatus(false);
    }

    @GetMapping("/signupRequest/restaurants") //view list of NON VERIFIED restaurants
    public List<Restaurant> getAllNonVerifiedRestaurants (){
        return restaurantService.getAllRestaurantsWithStatus(false);
    }

    //VERIFY USERS

    @PutMapping("/verify/nutritionist")  //verify nutritionist
    public void verifyRestaurant(User user,Nutritionist nutritionist){
        nutritionist.setStatus(true);
        userService.saveUser(user);
        nutritionistService.saveNutritionist(nutritionist);

    }
    @PutMapping("/verify/restaurant")  //verify restaurant
    public void verifyRestaurant(User user,Restaurant restaurant){

       restaurant.setStatus(true);
       userService.saveUser(user);
       restaurantService.saveRestaurant(restaurant);

    }

    //VIEW USERS
    @GetMapping("/viewPatients") //view list of Patients
    public List<Patient>  findAllPatients (){
        return patientService.getAllPatients();
    }
    @GetMapping("/viewCenters") //view list of nutritionist(centers)
    public List<Nutritionist> getAllCenters (){
        return nutritionistService.getAllCenters();
    }

    @GetMapping("/viewRestaurants") //view list of Restaurants
    public List<Restaurant> getAllRestaurants(){
        return restaurantService.getAllRestaurants();
    }

    //DELETE USERS
    @DeleteMapping("/deleteNutritionist") //delete nutritionist
    public void deleteNutritionistById(Long nutritionist_id) {
        nutritionistService.deleteNutritionistById(nutritionist_id);
    }
    @DeleteMapping("/deleteRestaurant")//delete restaurant
    public void deleteRestaurantById(Long restaurant_id) {
        restaurantService.deleteRestaurantById(restaurant_id);
    }
    @DeleteMapping("/deletePatient")//delete patient
    public void deletePatientById(Long patient_id) {
       patientService.deleteById(patient_id);
    }

    //REPORTS-ADMIN
    @GetMapping("/AllReports")//get list of the ALL reports includes that has been resolved
    public ResponseEntity<List<Report>> getAllReports() {
        List<Report> reports = reportService.getAllReports();
        return ResponseEntity.ok(reports);
    }
    @GetMapping("/notResolved/reports")  //get list of all visible report
    public ResponseEntity<List<Report>> getAllVisibleReports() {
        List<Report> reports = reportService.getAllVisibleReports();
        return ResponseEntity.ok(reports);
    }
    @GetMapping("/reports/{reportId}") //get content of specific report
    public ResponseEntity<String> showReportById(@PathVariable("reportId") Long reportId) {
        try {
            Report report = reportService.getReportById(reportId);
            String content=  "The Title" + report.getTitle() +"\n The Reason of the Report : " +report.getReason()+"\n"+ report.getRep_text();
            return ResponseEntity.ok(content);
        } catch (ReportNotFoundException e) {
            return ResponseEntity.notFound().build(); // Return HTTP 404 Not Found
        }
    }
    @PutMapping("/{report_id}/resolve") //resolve report
    public ResponseEntity<Void> resolveReport(@PathVariable("report_id") Long reportId) {
        try {
            reportService.resolveReport(reportId);
            return ResponseEntity.ok().build(); // Return HTTP 200 OK
        } catch (ReportNotFoundException e) {
            return ResponseEntity.notFound().build(); // Return HTTP 404 Not Found
        }
    }
    @DeleteMapping("/delete/{reportId}")//delete report
    public ResponseEntity<Void> deleteReport(@PathVariable("reportId") Long reportId) {
        try {
            reportService.deleteReport(reportId);
            return ResponseEntity.noContent().build(); // Return HTTP 204 No Content
        } catch (ReportNotFoundException e) {
            return ResponseEntity.notFound().build(); // Return HTTP 404 Not Found
        }
    }

    }

