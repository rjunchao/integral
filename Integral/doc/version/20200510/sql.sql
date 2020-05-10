USE integral_new;
/*礼品申请表添加采购状态*/
ALTER TABLE `integral_new`.`gd_org_apply_product`   
  CHANGE `def1` `def1` CHAR(1) CHARSET utf8 COLLATE utf8_general_ci NULL  COMMENT '采购状态';
ALTER TABLE `integral_new`.`gd_org_apply_product`   
  CHANGE `def2` `def2` VARCHAR(10) CHARSET utf8 COLLATE utf8_general_ci NULL  COMMENT '实际采购数量';

/*给积分兑换表添加索引*/
ALTER TABLE `integral_new`.`gd_sub_integral_detail`   
  ADD PRIMARY KEY (`pk_integral_detail`),
  ADD INDEX (`ts`),
  ADD INDEX (`option_org`);
/*给VIP积分赠送添加索引*/
ALTER TABLE `integral_new`.`gd_vip_integral_detail`   
  ADD PRIMARY KEY (`pk_integral_detail`),
  ADD INDEX (`ts`),
  ADD INDEX (`create_user_org`);
/*给活动赠送添加索引*/
ALTER TABLE `integral_new`.`gd_ap_integral_detail`   
  CHANGE `pk_ap_integral_detail` `pk_ap_integral_detail` VARCHAR(30) CHARSET utf8 COLLATE utf8_general_ci NOT NULL, 
  ADD PRIMARY KEY (`pk_ap_integral_detail`),
  ADD INDEX (`ts`),
  ADD INDEX (`create_user_org`);

  