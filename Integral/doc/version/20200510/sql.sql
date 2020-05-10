/*礼品申请表添加采购状态*/
ALTER TABLE `integral_new`.`gd_org_apply_product`   
  CHANGE `def1` `def1` CHAR(1) CHARSET utf8 COLLATE utf8_general_ci NULL  COMMENT '采购状态';
/*给积分兑换表添加索引*/
ALTER TABLE `integral_new`.`gd_sub_integral_detail`   
  ADD PRIMARY KEY (`pk_integral_detail`),
  ADD INDEX (`ts`),
  ADD INDEX (`option_org`);
  
  