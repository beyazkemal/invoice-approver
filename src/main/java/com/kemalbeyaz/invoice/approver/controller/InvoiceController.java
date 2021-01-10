package com.kemalbeyaz.invoice.approver.controller;

import com.kemalbeyaz.invoice.approver.InvoiceService;
import com.kemalbeyaz.invoice.approver.model.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author kemal.beyaz
 */
@RestController
@RequestMapping(value = "invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping
    public Invoice save(@Valid @RequestBody Invoice invoice, BindingResult bindingResult) {
        checkBindingResult(bindingResult);
        return invoiceService.save(invoice);
    }

    @GetMapping
    public List<Invoice> findAll() {
        return invoiceService.findAll();
    }

    @GetMapping("approved")
    public List<Invoice> findAllApproved() {
        return invoiceService.findAllApproved();
    }

    @GetMapping("/non-approved")
    public List<Invoice> findAllNonApproved() {
        return invoiceService.findAllNonApproved();
    }

    /**
     * Invoice sınıfında tanımlanan validasyonların kontrolünü sağlar.
     *
     * @see Invoice
     */
    private void checkBindingResult(BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            return;
        }

        String collectedErrors = bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));
        throw new IllegalArgumentException(collectedErrors);
    }
}
