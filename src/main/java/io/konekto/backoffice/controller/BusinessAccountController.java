package io.konekto.backoffice.controller;

import io.konekto.backoffice.domain.dto.BusinessAccountDto;
import io.konekto.backoffice.domain.dto.request.BusinessAccountActionRequestDTO;
import io.konekto.backoffice.domain.dto.request.BusinessAccountRequestDTO;
import io.konekto.backoffice.service.BusinessAccountService;
import io.konekto.backoffice.service.FileExportService;
import io.konekto.backoffice.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BusinessAccountController {

    @Autowired
    BusinessAccountService businessAccountService;

    @Autowired
    FileExportService fileExportService;

    @GetMapping("/business-account")
    public ResponseEntity<List<BusinessAccountDto>> getBusinessAccounts(BusinessAccountRequestDTO request, Pageable pageable) {

        Page<BusinessAccountDto> page = businessAccountService.getBusinessAccounts(request, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/business-account/{id}")
    public ResponseEntity<BusinessAccountDto> getBusinessAccount(@PathVariable String id) {

        BusinessAccountDto DTO = businessAccountService.getBusinessAccount(id);
        return ResponseEntity.ok(DTO);
    }

    @PostMapping("/business-account/action")
    public ResponseEntity<Void> actionBusinessVerifyStatus(@RequestBody BusinessAccountActionRequestDTO requestDTO) {

        businessAccountService.updateBusinessVerifyStatus(requestDTO);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/business-account/download")
    public ResponseEntity<Resource> getFile() {
        String filename = "business_report.csv";
        InputStreamResource file = new InputStreamResource(fileExportService.businessAccountsToCSV());

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
            .contentType(MediaType.parseMediaType("application/csv"))
            .body(file);
    }

}
