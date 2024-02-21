package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.domain.Administrator;
import com.example.form.InsertAdministratorForm;
import com.example.form.LoginForm;
import com.example.service.AdministratorService;

import jakarta.servlet.http.HttpSession;

/**
 * 管理者情報を操作するコントローラー.
 * 
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping("/")
public class AdministratorController {

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private HttpSession session;

	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		return new InsertAdministratorForm();
	}

	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
	}

	/////////////////////////////////////////////////////
	// ユースケース：管理者を登録する
	/////////////////////////////////////////////////////
	/**
	 * 管理者登録画面を出力します.
	 * 
	 * @return 管理者登録画面
	 */
	@GetMapping("/toInsert")
	public String toInsert(Model model, InsertAdministratorForm form) {
		return "administrator/insert";
	}

	/**
	 * 管理者情報を登録します.
	 * 
	 * @param form 管理者情報用フォーム
	 * @return ログイン画面へリダイレクト
	 */
	@PostMapping("/insert")
	public String insert(@Validated InsertAdministratorForm form, BindingResult result,RedirectAttributes redirectAttributes, Model model) {
		if (result.hasErrors()) {
			return toInsert(model, form);
		}
		if(administratorService.isMailAddressExists(form.getMailAddress())) {
			result.rejectValue("mailAddress", "error.mailAddress", "既に登録されているメールアドレスです");
			return toInsert(model, form);
		}
		if(!form.getPassword().equals(form.getConfirmPassword())) {
			// パスワードと確認用パスワードが一致しない場合の処理
			result.rejectValue("confirmPassword", "password.mismatch", "パスワードが一致しません");
			return toInsert(model, form);
		}
		Administrator administrator = new Administrator();
		// フォームからドメインにプロパティ値をコピー
		BeanUtils.copyProperties(form, administrator);
		//administratorServiceのinsertメソッドを呼び出す
		administratorService.insert(administrator);
		//1-4 ダブルサブミット対策
		redirectAttributes.addFlashAttribute("successMessage", "登録が完了しました");
		// ログイン画面にリダイレクト
		return "redirect:/";
	}

	/////////////////////////////////////////////////////
	// ユースケース：ログインをする
	/////////////////////////////////////////////////////
	/**
	 * ログイン画面を出力します.
	 * @param form 
	 * @param model 
	 * 
	 * @return ログイン画面
	 */
	@GetMapping("/")
	public String toLogin(Model model, LoginForm form) {
		return "administrator/login";
	}

	/**
	 * ログインします.
	 * 
	 * @param form 管理者情報用フォーム
	 * @param result
	 * @param redirectAttributes
	 * @return ログイン後の従業員一覧画面
	 */
	@PostMapping("/login")
    public String login(@Validated LoginForm form, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if(result.hasErrors()) {
            return toLogin(model, form);
        }

        Administrator administrator = administratorService.login(form.getMailAddress(), form.getPassword());
        if (administrator == null) {
            return "redirect:/";
        }
		session.setAttribute("administratorName", administrator.getName());
		return "redirect:/employee/showList";
    }

	/////////////////////////////////////////////////////
	// ユースケース：ログアウトをする
	/////////////////////////////////////////////////////
	/**
	 * ログアウトをします. (SpringSecurityに任せるためコメントアウトしました)
	 * 
	 * @return ログイン画面
	 */
	@GetMapping(value = "/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/";
	}

}
