package sg.edu.nus.visa.paf_day24.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.nus.visa.paf_day24.exception.AccountBlockedAndDisabledException;
import sg.edu.nus.visa.paf_day24.exception.AmountNotSufficientException;
import sg.edu.nus.visa.paf_day24.model.BankAccount;
import sg.edu.nus.visa.paf_day24.repository.BankAccountRepo;

@Service
public class BankAccountService {
    
    @Autowired
    BankAccountRepo bankAccountRepo;

    public BankAccount retrieveAccountById(Integer accountId){
        return bankAccountRepo.getAccountById(accountId);
    }

    public Boolean createAccount(BankAccount bankAccount){
        return bankAccountRepo.createAccount(bankAccount);
    }

    //transactional: encompass in a single unit of work / "atomic"
    //writing of records to more than one tables or
    //update more then one records in the same table

    @Transactional
    public Boolean transferMoney(Integer withdrawAccountId, Integer depositAccountId, Float transferAmount){
        //logic 1. check that receiving account exists
        Boolean bDepositExist = false;
        BankAccount depositA = bankAccountRepo.getAccountById(depositAccountId);

        if (depositA !=null){
            bDepositExist = true;
        }

        //2. check that giving account exists

        Boolean bWithdrawExist = false;
        BankAccount withdrawA = bankAccountRepo.getAccountById(withdrawAccountId);

        if (withdrawA!=null){
            bWithdrawExist = true;
        }

        // 3. check giving account active and transferer not blocked
        Boolean bTransfererAllowed = false;
        if (withdrawA.getIsActive() && !withdrawA.getIsBlocked()){
            bTransfererAllowed=true;
        }

        //4. check receiver is active and not blocked

        Boolean bReceiverAllowed = false;
        if (depositA.getIsActive() && !depositA.getIsBlocked()){
            bReceiverAllowed=true;
        }

        // 5. check giving account has enough money
        Boolean bEnoughMoney = false;
        if (withdrawA.getBalance()>transferAmount){
            bEnoughMoney = true;
        }

        if (bDepositExist && bWithdrawExist && bTransfererAllowed && bReceiverAllowed && bEnoughMoney){
            // can simulate exception here to check transactional

            // carry out transfer operation
            // both of these must be successful in one unit of work
            // anywhere in this function fails, it will rollback
            // 1. withdraw the amount from giver account

            bankAccountRepo.withdrawAmount(withdrawAccountId, transferAmount);

            //2. depoosit the amount to receiving account
            bankAccountRepo.depositAmount(depositAccountId, transferAmount);


        }else{
            if (!bTransfererAllowed){
                throw new AccountBlockedAndDisabledException("Transferer is either blocked or inactive");
            }

            if (!bReceiverAllowed){
                throw new AccountBlockedAndDisabledException("Receiver is either blocked or inactive");
            }

            if (!bEnoughMoney){
                throw new AmountNotSufficientException("Transferer doesn't have enough balance");
            }

        }

        return true;
    }

    

}
