package com.tests.testsapp;

import com.tests.testsapp.services.ClassAccessorService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class TestsappApplicationTests {

	@Test
	void contextLoads() {
	}
	@Test
	public void testClassAccessor()  {
		ClassAccessorService accessorService = new ClassAccessorService();
		boolean failed = false;
		try {
			accessorService.getAllClasses("plugins");
		}
		catch (Exception e){
			failed = true;
			e.printStackTrace();

		}
		finally {
			Assert.isTrue(!failed);
		}
	}

	@Test
	public void testManySerialize()  {
		Assert.isTrue(serialize("{\"name\":\"dsadas\",\"questions\":[{\"q_content\":\"dsadsa\",\"type\":\"Вопрос с множественным ответом\",\"answers\":\"<div class=\\\"d-flex flex-column align-items-center\\\" style=\\\"gap: 10px\\\">\\n  <span class=\\\"m-0\\\" style=\\\"align-self: start\\\">Конструктор</span>\\n  <div id=\\\"allAnswers\\\" class=\\\"d-flex flex-column w-100\\\" style=\\\"gap: 10px\\\">\\n    <div id=\\\"newAnswer\\\" class=\\\"d-flex align-items-center\\\" style=\\\"gap: 10px\\\">\\n      <input class=\\\"form-check-input\\\" type=\\\"checkbox\\\" name=\\\"trueAnswer\\\" onclick=\\\"this.setAttribute('checked', this.checked)\\\" value=\\\"asdsadsa\\\" checked=\\\"true\\\">\\n      <input type=\\\"text\\\" class=\\\"form-control\\\" placeholder=\\\"Текст ответа\\\" name=\\\"answerText\\\" onkeyup=\\\"changeRadioValue(this)\\\">\\n      <button class=\\\"btn btn-danger\\\" onclick=\\\"deleteVar(this)\\\">-</button>\\n    </div>\\n  <div id=\\\"newAnswer\\\" class=\\\"d-flex align-items-center\\\" style=\\\"gap: 10px\\\">\\n      <input class=\\\"form-check-input\\\" type=\\\"checkbox\\\" name=\\\"trueAnswer\\\" onclick=\\\"this.setAttribute('checked', this.checked)\\\" value=\\\"asdsadsa\\\" checked=\\\"true\\\">\\n      <input type=\\\"text\\\" class=\\\"form-control\\\" placeholder=\\\"Текст ответа\\\" name=\\\"answerText\\\" onkeyup=\\\"changeRadioValue(this)\\\">\\n      <button class=\\\"btn btn-danger\\\" onclick=\\\"deleteVar(this)\\\">-</button>\\n    </div><div id=\\\"newAnswer\\\" class=\\\"d-flex align-items-center\\\" style=\\\"gap: 10px\\\">\\n      <input class=\\\"form-check-input\\\" type=\\\"checkbox\\\" name=\\\"trueAnswer\\\" onclick=\\\"this.setAttribute('checked', this.checked)\\\" value=\\\"asdsadsa\\\" checked=\\\"false\\\">\\n      <input type=\\\"text\\\" class=\\\"form-control\\\" placeholder=\\\"Текст ответа\\\" name=\\\"answerText\\\" onkeyup=\\\"changeRadioValue(this)\\\">\\n      <button class=\\\"btn btn-danger\\\" onclick=\\\"deleteVar(this)\\\">-</button>\\n    </div></div>\\n  <button class=\\\"btn btn-primary\\\" onclick=\\\"add(this)\\\" id=\\\"addAnswer\\\">+</button>\\n</div>\"},{\"q_content\":\"dsadsadasas\",\"type\":\"Вопрос с множественным ответом\",\"answers\":\"<div class=\\\"d-flex flex-column align-items-center\\\" style=\\\"gap: 10px\\\">\\n  <span class=\\\"m-0\\\" style=\\\"align-self: start\\\">Конструктор</span>\\n  <div id=\\\"allAnswers\\\" class=\\\"d-flex flex-column w-100\\\" style=\\\"gap: 10px\\\">\\n    <div id=\\\"newAnswer\\\" class=\\\"d-flex align-items-center\\\" style=\\\"gap: 10px\\\">\\n      <input class=\\\"form-check-input\\\" type=\\\"checkbox\\\" name=\\\"trueAnswer\\\" onclick=\\\"this.setAttribute('checked', this.checked)\\\" value=\\\"sadsadsasad\\\" checked=\\\"true\\\">\\n      <input type=\\\"text\\\" class=\\\"form-control\\\" placeholder=\\\"Текст ответа\\\" name=\\\"answerText\\\" onkeyup=\\\"changeRadioValue(this)\\\">\\n      <button class=\\\"btn btn-danger\\\" onclick=\\\"deleteVar(this)\\\">-</button>\\n    </div>\\n  </div>\\n  <button class=\\\"btn btn-primary\\\" onclick=\\\"add(this)\\\" id=\\\"addAnswer\\\">+</button>\\n</div>\"}]}"));
	}
	public boolean serialize(String html) {
		try {
			Document answersHtml = Jsoup.parse(html);
			JSONObject obj = new JSONObject();
			obj.put("answers", new JSONArray());
			obj.put("questions", new JSONArray());
			int i = 0;
			for (Element el : answersHtml.getElementsByTag("input")
			) {
				if (el.toString().contains("this.setAttribute")) {
					if (el.attributes().get("checked").contains("true")) {
						obj.getJSONArray("answers").put(i);
						obj.getJSONArray("questions").put(el.val());
					}

				}
				i++;
			}
			return true;
		} catch (Exception e) {
			return false;
		}

	}
}
