package com.ecarinfo.survey.test;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import junit.SimpleTest;

import org.junit.Assert;

import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.persist.service.GenericService;
import com.ecarinfo.survey.po.SurveyUserInfo;
import com.ecarinfo.survey.service.SurveyUserInfoService;

public class SurveyUserInfoTest extends SimpleTest {

	@Resource
	private SurveyUserInfoService surveyUserInfoService;

	@Resource
	private GenericService genericService;

	// 保存
	@org.junit.Test
	public void testSave() {
		SurveyUserInfo dto = new SurveyUserInfo();
		genericService.save(dto);
		Assert.assertTrue(dto.getId() > 0);
	}

	// 修改
	@org.junit.Test
	public void testUpdate() {
		SurveyUserInfo dto = new SurveyUserInfo();
		genericService.save(dto);
		Date date = DateUtils.stringToDate("1988-01-17 12:12:12", "yyyy-MM-dd hh:mm:ss");
		dto.setCreateTime(date);
		genericService.update(dto);
		Integer id = dto.getId();
		SurveyUserInfo newDto = this.genericService.findByPK(SurveyUserInfo.class, id);
		Assert.assertEquals(date.getTime(), newDto.getCreateTime().getTime());
	}

	// 删除
	@org.junit.Test
	public void testDelete() {
		SurveyUserInfo dto = new SurveyUserInfo();
		genericService.save(dto);
		genericService.deleteEntity(dto);
		Integer id = dto.getId();
		Assert.assertNull(this.genericService.findByPK(SurveyUserInfo.class, id));

	}

	// 按Id查询
	@org.junit.Test
	public void testFindByPK() {
		SurveyUserInfo dto = this.genericService.findByPK(SurveyUserInfo.class, 1);
		Assert.assertTrue(dto.getId() > 0);
		System.err.println(dto);
	}

	// 获取所有
	@org.junit.Test
	public void testFindAll() {
		List<SurveyUserInfo> dots = this.genericService.findAll(SurveyUserInfo.class);
		System.err.println("SurveyUserInfo size:" + dots.size());
		for (SurveyUserInfo dto : dots) {
			System.err.println(dto);
		}
	}
}
