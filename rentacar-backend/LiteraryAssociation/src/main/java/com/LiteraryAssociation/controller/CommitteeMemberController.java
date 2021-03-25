package com.LiteraryAssociation.controller;

import com.LiteraryAssociation.dto.FormFieldDTO;
import com.LiteraryAssociation.dto.OpinionDTO;
import com.LiteraryAssociation.enums.Opinion;
import com.LiteraryAssociation.model.CommitteMemberWriter;
import com.LiteraryAssociation.service.CommitteeMemberWriterService;
import com.LiteraryAssociation.service.WriterService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/committee-member", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommitteeMemberController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private FormService formService;

    @Autowired
    private WriterService writerService;

    @Autowired
    private CommitteeMemberWriterService committeeMemberWriterService;


    @GetMapping(value = "/getCommitteeMemberForm")
    public List<FormFieldDTO> getCommitteeMemberForm() {

        List<ProcessInstance> processInstances =
                runtimeService.createProcessInstanceQuery()
                        .processDefinitionKey("upp_process_1")
                        .active() // we only want the unsuspended process instances
                        .list();

        List<FormFieldDTO> ret = new ArrayList<>();

        for (ProcessInstance processInstance : processInstances) {
            if(taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().size() > 0 ) {
                Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).list().get(0);
                System.out.println("getcommitteememeber task: " + task.getTaskDefinitionKey());

                if (task.getTaskDefinitionKey().equals("clanovi_odbora_daju_misljenje") &&
                        !committeeMemberWriterService.alreadyCommented(
                                (Long) runtimeService.getVariable(processInstance.getId(), "writerId"),
                                (Integer) runtimeService.getVariable(processInstance.getId(), "numOfSending"))) {

                    TaskFormData tfd = formService.getTaskFormData(task.getId());
                    List<FormField> properties = tfd.getFormFields();


                    for (FormField field : properties) {
                        if (field.getId().equals("misljenje")) {
                            EnumFormType enumType = (EnumFormType) field.getType();
                            enumType.getValues().clear();
                            enumType.getValues().put(Opinion.PODOBAN.toString(), Opinion.PODOBAN.toString());
                            enumType.getValues().put(Opinion.NIJE_PODOBAN.toString(), Opinion.NIJE_PODOBAN.toString());
                            enumType.getValues().put(Opinion.JOS_MATERIJALA.toString(), Opinion.JOS_MATERIJALA.toString());
                        }

                    }


                    ret.add(new FormFieldDTO(task.getId(), processInstance.getId(), properties));
                }
            }

        }

        if (ret.size() > 0)
            return ret;
        else
            return null;

    }

    @PostMapping(value = "/{proccessId}/{taskId}")
    public void addOpinion(@RequestBody OpinionDTO opinionDTO,
                           @PathVariable("proccessId") String proccessId, @PathVariable("taskId") String taskId) {
        System.out.println("usao u addOpinion");
        System.out.println(opinionDTO);
        Integer numOfSending = (Integer) runtimeService.getVariable(proccessId, "numOfSending");
        CommitteMemberWriter committeMemberWriter = new CommitteMemberWriter();
        committeMemberWriter.setComment(opinionDTO.getComment());
        committeMemberWriter.setOpinion(Opinion.valueOf(opinionDTO.getOpinion()));
        committeMemberWriter.setNumOfSending((Integer) runtimeService.getVariable(proccessId, "numOfSending"));
        boolean endTask = committeeMemberWriterService.addOpinion((Long) runtimeService.getVariable(proccessId, "writerId"),
                committeMemberWriter, numOfSending);
        System.out.println(endTask);
        if(endTask) {
            Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
            int result = committeeMemberWriterService.resultOfOpinion((Long) runtimeService.getVariable(proccessId, "writerId"),
                    numOfSending);
            runtimeService.setVariable(proccessId, "sviGlasali", true);
            runtimeService.setVariable(proccessId, "rezultat", result);
            String message = "";
            if(result == 1) {
                message = "You are not suitable to be a writer.";
            } else if(result == 2) {
                message = "You are suitable to be a writer.\n\nCongratulation! You are new member of association";
            } else {
                message = "You have to add more pdfs.";
            }
            logger.info("result of giving opinion: " + result);
            runtimeService.setVariable(proccessId, "message", message);
            taskService.complete(taskId);
        } else {
            runtimeService.setVariable(proccessId, "sviGlasali", false);
        }
    }

}
