package HeathTech.HealthTech.Service;

import HeathTech.HealthTech.Entirty.Hospital;
import HeathTech.HealthTech.Entirty.HospitalSearch;
import HeathTech.HealthTech.Entirty.NgoEntity;
import HeathTech.HealthTech.Entirty.Ngos;
import HeathTech.HealthTech.Repository.hospitaldatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindHospital {


    @Autowired
    private hospitaldatabase hospitaldatabase;

    public Page<Hospital> searchHospital(HospitalSearch hospitalSearch){
        Pageable pageable= PageRequest.of(0,10, Sort.by("hospitalName").and(Sort.by("city")));
        return hospitaldatabase.findByHospitalName(hospitalSearch.getHospitalName(),hospitalSearch.getCity(), pageable);
    }

    //find hospiatl city base
    public ResponseEntity<?> findhospiatlcitybase(String City){
        try {
            List<Hospital> hopitals=hospitaldatabase.findByCity(City);
            return new ResponseEntity<>(hopitals, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
