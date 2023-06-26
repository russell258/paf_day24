package sg.edu.nus.visa.paf_day24.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import sg.edu.nus.visa.paf_day24.model.BankAccount;
import sg.edu.nus.visa.paf_day24.service.BankAccountService;

@RestController
@RequestMapping("/api/bankaccounts")
public class BankAccountController {
    
    @Autowired
    BankAccountService bankSvc;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Boolean> createAccount (@RequestBody BankAccount bankAccount){

        Boolean bAccountCreated = bankSvc.createAccount(bankAccount);
        if (bAccountCreated){
            return ResponseEntity.ok().body(bAccountCreated);
        }
        // exception / custom exception handling
        return ResponseEntity.internalServerError().body(bAccountCreated);
    }

    @GetMapping("/{account-id}")
    public ResponseEntity<BankAccount> retrieveAccountById(@PathVariable("account-id") Integer bankAccountId){
        BankAccount bAccount = bankSvc.retrieveAccountById(bankAccountId);
        return new ResponseEntity<BankAccount>(bAccount, HttpStatus.OK);
    }

}
