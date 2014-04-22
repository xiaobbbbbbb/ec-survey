package com.ecarinfo.survey.test;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import junit.SimpleTest;

import org.junit.Assert;

import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.persist.service.GenericService;
import com.ecarinfo.survey.po.TagInfo;
import com.ecarinfo.survey.service.TagInfoService;

public class TagInfoTest extends SimpleTest {

	@Resource
	private TagInfoService tagInfoService;

	@Resource
	private GenericService genericService;

	// 保存
	@org.junit.Test
	public void testSave() {
		TagInfo dto = new TagInfo();
		genericService.save(dto);
		Assert.assertTrue(dto.getId() > 0);
	}

	// 修改
	@org.junit.Test
	public void testUpdate() {
		TagInfo dto = new TagInfo();
		genericService.save(dto);
		Date date = DateUtils.stringToDate("1988-01-17 12:12:12", "yyyy-MM-dd hh:mm:ss");
		dto.setCreateTime(date);
		genericService.update(dto);
		Integer id = dto.getId();
		TagInfo newDto = this.genericService.findByPK(TagInfo.class, id);
		Assert.assertEquals(date.getTime(), newDto.getCreateTime().getTime());
	}

	// 删除
	@org.junit.Test
	public void testDelete() {
		TagInfo dto = new TagInfo();
		genericService.save(dto);
		genericService.deleteEntity(dto);
		Integer id = dto.getId();
		Assert.assertNull(this.genericService.findByPK(TagInfo.class, id));

	}

	// 按Id查询
	@org.junit.Test
	public void testFindByPK() {
		TagInfo dto = this.genericService.findByPK(TagInfo.class, 1);
		Assert.assertTrue(dto.getId() > 0);
		System.err.println(dto);
	}

	// 获取所有
	@org.junit.Test
	public void testFindAll() {
		List<TagInfo> dots = this.genericService.findAll(TagInfo.class);
		System.err.println("TagInfo size:" + dots.size());
		for (TagInfo dto : dots) {
			System.err.println(dto);
		}
	}
}
