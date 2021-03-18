package com.controller;

import com.ExceptionHandling.AppExceptions;
import com.configuration.AppLogger;
import com.dao.CommonRepository;
import com.dto.request.DateValidatorRequest;
import com.dto.request.UpdateRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.model.DateValidator;
import com.utils.GenericDateUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author avinash.a.mishra
 */
@RestController
@RequestMapping("/ar/v1")
public class TestController {
    @Autowired
    private CommonRepository commonRepository;

    @PostMapping("/checkDate/update/{id}")
    public String checkDate(@PathVariable(required = false)Long id) throws JsonProcessingException {
        String result = addDates(id);
        return result;
    }

    @GetMapping("/checkDate/fetch/{id}")
    public DateValidator fetchDate(@PathVariable Long id) throws JsonProcessingException {

        DateValidator byId = commonRepository.findById(DateValidator.class, id);
        if (byId!=null){
            AppLogger.RECHARGE_SCHEDULER.info(byId.toStringForLogger());
        }

        return byId;
    }


    @GetMapping("/checkDate/fetch")
    public List checkDate(@RequestBody @Valid DateValidatorRequest request) throws JsonProcessingException, ParseException {

        if(request.getOption().equalsIgnoreCase("i")){
            return instanceCase(request);
        }else if(request.getOption().equalsIgnoreCase("l")){
            return localDateCase(request);
        }else if(request.getOption().equalsIgnoreCase("u")){
            return utilDateCase(request);
        }

        return null;
    }

    @PostMapping("/update")
    public String update(@RequestBody @Valid UpdateRequest request) throws JsonProcessingException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        int rowAffected=0;
        try {
            rowAffected = commonRepository.runNativeQuery(request);
        }catch (Exception e){

            throw new AppExceptions(e.getMessage()+"--"+e.getCause()+"-"+e.getClass());
        }
        return "Row affected :"+rowAffected;

    }


    @GetMapping("/lastRecords/{className}/{n}")
    public List getLastRecords(@PathVariable(required = false)String className, @PathVariable(required = false)Integer n) throws JsonProcessingException {
        String json="";
        List finalList= new ArrayList();
        try{

            finalList= commonRepository.getLastRecords(className, n);

        }catch (Exception e){
            throw new AppExceptions(e.getMessage()+"--"+e.getCause()+"-"+e.getClass());
        }
        return finalList;
    }


    @GetMapping("/recordWithId/{className}/{id}")
    public List getRecordsWithId(@PathVariable(required = false)String className, @PathVariable(required = false)Long id) throws JsonProcessingException {
        String json="";
        List finalList = new ArrayList();
        try {

            finalList = commonRepository.getRecordWithId(className, id);


        }catch (Exception e){
            throw new AppExceptions(e.getMessage()+"--"+e.getCause()+"-"+e.getClass());
        }
        return finalList;
    }




    public List instanceCase(DateValidatorRequest request){
        Instant instant1 = Instant.parse(request.getFromDate());
        Instant instant2 = Instant.parse(request.getToDate());
        AppLogger.RECHARGE_SCHEDULER.info("instant "+instant1+": AND :" +instant2);
        String queryString="FROM DateValidator WHERE instant>=:param1 AND instant<=:param2";
        List list = commonRepository.executeSelect(queryString, instant1,instant2);
        System.out.println();
        return list;
    }


    public List utilDateCase(DateValidatorRequest request) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(GenericDateUtils.instantFormat);
        Date date1 = format.parse(request.getFromDate());
        Date date2 = format.parse(request.getToDate());
        AppLogger.RECHARGE_SCHEDULER.info("util "+date1+": AND :" +date2);
        String queryString="FROM DateValidator WHERE util>=:param1 AND util<=:param2";
        List list = commonRepository.executeSelect(queryString,date1,date2);
        System.out.println();
        return list;
    }

    public List localDateCase(DateValidatorRequest request){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(GenericDateUtils.instantFormat);
        LocalDateTime ldt1 = LocalDateTime.parse(request.getFromDate(), formatter);
        LocalDateTime ldt2 = LocalDateTime.parse(request.getToDate(), formatter);
        AppLogger.RECHARGE_SCHEDULER.info("local "+ldt1+": AND :" +ldt2);
        String queryString="FROM DateValidator WHERE local>=:param1 AND local<=:param2";
        List list = commonRepository.executeSelect(queryString, ldt1,ldt2);
        System.out.println();
        return list;
    }

    public String addDates(Long id) {
        DateValidator byId = commonRepository.findById(DateValidator.class, id);
        if(byId!=null) {
            addDates(byId);
            return  "updated";
        }else {
            DateValidator dateValidator= new DateValidator();
            addDates(dateValidator);
            return  "new Data added";
        }

    }

    public void addDates(DateValidator byId) {
        byId.setUtil(new Date());
        byId.setLocal(LocalDateTime.now());
        byId.setInstant(Instant.now());
        byId.setLocalDay(LocalDate.now());
        //byId.setJoda(DateTime.now());
        commonRepository.saveOrUpdate(byId);
    }
}
