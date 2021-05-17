package io.konekto.backoffice.service.impl;

import io.konekto.backoffice.domain.dto.BusinessAccountDto;
import io.konekto.backoffice.domain.dto.request.BusinessAccountRequestDTO;
import io.konekto.backoffice.exception.BadRequestException;
import io.konekto.backoffice.service.BusinessAccountService;
import io.konekto.backoffice.service.FileExportService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

@Service
public class FileExportServiceImpl implements FileExportService {

    @Autowired
    BusinessAccountService businessAccountService;

    @Override
    public ByteArrayInputStream businessAccountsToCSV() {
        Page<BusinessAccountDto> dataList = businessAccountService.getBusinessAccounts(new BusinessAccountRequestDTO(), PageRequest.of(0, 1000));
        if (CollectionUtils.isEmpty(dataList.getContent())) {
            return null;
        }

        String pattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {

            List<String> header = Arrays.asList(
                "User Id",
                "Bussiness Name",
                "Owner Name",
                "Email",
                "Mobile",
                "Date",
                "Status"
            );
            csvPrinter.printRecord(header);

            for (BusinessAccountDto businessAccount : dataList.getContent()) {
                List<String> data = Arrays.asList(
                    businessAccount.getUserId(),
                    businessAccount.getBusiness().getName(),
                    businessAccount.getPersonal().getName(),
                    businessAccount.getBusiness().getEmail(),
                    businessAccount.getBusiness().getMobile(),
                    dateFormat.format(businessAccount.getBusiness().getCreatedAt()),
                    businessAccount.getBusiness().getVerifyStatus()
                );

                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            throw new BadRequestException("Fail to import data to CSV file: " + e.getMessage());
        }
    }
}
