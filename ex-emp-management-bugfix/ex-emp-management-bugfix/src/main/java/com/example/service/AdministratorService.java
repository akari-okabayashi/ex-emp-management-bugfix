package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Administrator;
import com.example.repository.AdministratorRepository;

/**
 * 管理者情報を操作するサービス.
 * 
 * @author igamasayuki
 *
 */
@Service
@Transactional
public class AdministratorService {

	@Autowired
	private AdministratorRepository administratorRepository;

	/**
	 * 管理者情報を登録します.
	 * 
	 * @param administrator 管理者情報
	 */
	public void insert(Administrator administrator) {
		if(isMailAddressExists(administrator.getMailAddress())) {
			throw new DuplicateKeyException("そのメールアドレスは既に登録されています");
		}
		administratorRepository.insert(administrator);
	}

	/**
	 * ログインをします.
	 * 
	 * @param mailAddress メールアドレス
	 * @param password    パスワード
	 * @return 管理者情報 存在しない場合はnullが返ります
	 */
	public Administrator login(String mailAddress, String password) {
		Administrator administrator = administratorRepository.findByMailAddressAndPassward(mailAddress, password);
		return administrator;
	}

	public boolean isMailAddressExists(String mailAddress) {
		return administratorRepository.findByMailAddress(mailAddress) != null;
	}

    public void updateAdministrator(Administrator administrator) {
        throw new UnsupportedOperationException("Unimplemented method 'updateAdministrator'");
    }

    public List<Administrator> getAllAdministrators() {
        throw new UnsupportedOperationException("Unimplemented method 'getAllAdministrators'");
    }


}	
