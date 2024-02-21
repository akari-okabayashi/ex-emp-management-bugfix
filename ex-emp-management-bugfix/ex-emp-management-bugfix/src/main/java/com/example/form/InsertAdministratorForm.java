package com.example.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 * 管理者情報登録時に使用するフォーム.
 * 
 * @author igamasayuki
 * 
 */
public class InsertAdministratorForm {
	/** 名前 */
	@NotEmpty(message = "名前を入力してください")
	private String name;
	/** メールアドレス */
	@Email(message = "メールアドレスの形式が不正です")
	@NotEmpty(message = "メールアドレスを入力してください")
	private String mailAddress;
	/** パスワード */
	@NotEmpty(message = "パスワードを入力してください")
	@Size(min = 8, max = 15, message = "パスワードは8文字以上15文字以下で入力してください")
	private String password;
	/** 確認用パスワード */
	@NotEmpty(message = "パスワードを入力してください")
	private String confirmPassword;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "InsertAdministratorForm [name=" + name + ", mailAddress=" + mailAddress + ", password=" + password
				+ "]";
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
