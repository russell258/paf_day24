package sg.edu.nus.visa.paf_day24.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import sg.edu.nus.visa.paf_day24.model.BankAccount;

@Repository
public class BankAccountRepo {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String GET_ACCOUNT_SQL = "select * from bank_account where id = ?";
    private final String WITHDRAW_SQL = "update bank_account set balance = balance - ? where id =?";
    private final String DEPOSIT_SQL = "update bank_account set balance = balance + ? where id = ?";

    private final String CREATE_ACCOUNT_SQL = "insert into bank_account (full_name, is_blocked, is_active, account_type, balance) values (?,?,?,?,?)";
    
    public BankAccount getAccountById(Integer bankAccountId){
        BankAccount bankAccount = jdbcTemplate.queryForObject(GET_ACCOUNT_SQL, BeanPropertyRowMapper.newInstance(BankAccount.class),bankAccountId);
        return bankAccount;
    }

    public Boolean withdrawAmount(Integer withdrawAccountId, Float withdrawAmount){

        Integer iResult = jdbcTemplate.update(WITHDRAW_SQL,withdrawAmount, withdrawAccountId);
        return iResult > 0 ? true: false;
    }

    public Boolean depositAmount(Integer depositAccountId, Float depositAmount){
        Integer iResult = jdbcTemplate.update(DEPOSIT_SQL, depositAccountId, depositAmount);
        return iResult>0 ? true: false;
    }

    public Boolean createAccount(BankAccount bankAccount){

        Integer iResult = jdbcTemplate.update(CREATE_ACCOUNT_SQL, bankAccount.getFullName(), bankAccount.getIsBlocked(), 
                                        bankAccount.getIsActive(), bankAccount.getAccountType(), bankAccount.getBalance());

        return iResult > 0 ? true: false;
    }

    // @Transactional
    // public Boolean transferMoney (Integer withdrawAccountId, Integer depositAccountId, Float transferAmount){
        
    // }

}
