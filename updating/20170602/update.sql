-- 更新密码
UPDATE `sys_user` SET `password` = 'NEA1HP2PP1128801AN68NE2IN60NN6IC' WHERE `username` = 'supreme';
-- 文件上传
---- 附件删除
INSERT INTO `common-web-general`.`sys_action` (`id`, `code`, `create_time`, `description`, `method`, `modify_time`, `name`, `url`, `state_id`) VALUES ('755E8F300C4E470B9C09DFB1937A5439', 'tax.business.attachment.del', '2017-06-01 22:32:18', NULL, NULL, '2017-06-01 22:32:18', '删除附件', 'app/tax/business/attachment/del', '5A6E2DFC64884E04AF66F094389531B3');
INSERT INTO `common-web-general`.`sys_action` (`id`, `code`, `create_time`, `description`, `method`, `modify_time`, `name`, `url`, `state_id`) VALUES ('8865E59E2FE4461796D0FCE003D963A2', 'tax.business.attachment.del', '2017-06-01 22:31:44', NULL, NULL, '2017-06-01 22:31:44', '删除附件', 'app/tax/business/attachment/del', '45FE5B6350BB4FE785AABC13DDED4133');
------ 权限(管理员、分局执法人员)
INSERT INTO `common-web-general`.`sys_role_action` (`role_id`, `action_id`) VALUES ('8DFEBE814BDD475A9E455D34500333E0', '755E8F300C4E470B9C09DFB1937A5439');
INSERT INTO `common-web-general`.`sys_role_action` (`role_id`, `action_id`) VALUES ('AAFDAF8B7B6F46D4AD18E7AFA578AA98', '755E8F300C4E470B9C09DFB1937A5439');
INSERT INTO `common-web-general`.`sys_role_action` (`role_id`, `action_id`) VALUES ('8DFEBE814BDD475A9E455D34500333E0', '8865E59E2FE4461796D0FCE003D963A2');
INSERT INTO `common-web-general`.`sys_role_action` (`role_id`, `action_id`) VALUES ('AAFDAF8B7B6F46D4AD18E7AFA578AA98', '8865E59E2FE4461796D0FCE003D963A2');
---- 附件列表
INSERT INTO `common-web-general`.`sys_action` (`id`, `code`, `create_time`, `description`, `method`, `modify_time`, `name`, `url`, `state_id`) VALUES ('05CB003C4A3E40E7BEA67AD8E5AFF6A2', 'tax.business.attachment.list', '2017-06-01 15:10:52', NULL, NULL, '2017-06-01 15:10:52', '附件列表', 'app/tax/business/attachment/list', '45FE5B6350BB4FE785AABC13DDED4133');
INSERT INTO `common-web-general`.`sys_action` (`id`, `code`, `create_time`, `description`, `method`, `modify_time`, `name`, `url`, `state_id`) VALUES ('85AD3C49171D4E26B878661966932447', 'tax.business.attachment.list', '2017-06-01 15:13:04', NULL, NULL, '2017-06-01 15:13:04', '附件列表', 'app/tax/business/attachment/list', '5A6E2DFC64884E04AF66F094389531B3');
INSERT INTO `common-web-general`.`sys_action` (`id`, `code`, `create_time`, `description`, `method`, `modify_time`, `name`, `url`, `state_id`) VALUES ('93C5D9A36E34411DB480CA5CB56C546B', 'tax.business.attachment.list', '2017-06-02 16:03:28', NULL, NULL, '2017-06-02 16:03:28', '附件列表', 'app/tax/business/attachment/list', '2F53F0EC9CCC46E5970C3CFEF10CBEA3');
INSERT INTO `common-web-general`.`sys_action` (`id`, `code`, `create_time`, `description`, `method`, `modify_time`, `name`, `url`, `state_id`) VALUES ('982E2172736B4836999F71048A1C6D24', 'tax.business.attachment.list', '2017-06-01 15:12:31', NULL, NULL, '2017-06-01 15:12:31', '附件列表', 'app/tax/business/attachment/list', '85D85C3210184D21B7B0949FE34756AB');
INSERT INTO `common-web-general`.`sys_action` (`id`, `code`, `create_time`, `description`, `method`, `modify_time`, `name`, `url`, `state_id`) VALUES ('D00A50A694A04DAB841F87C739D1B272', 'tax.business.attachment.list', '2017-06-02 16:02:57', NULL, NULL, '2017-06-02 16:02:57', '附件列表', 'app/tax/business/attachment/list', '03A5F34C27414F00945153F863D00EFE');
INSERT INTO `common-web-general`.`sys_action` (`id`, `code`, `create_time`, `description`, `method`, `modify_time`, `name`, `url`, `state_id`) VALUES ('F10B35767692422F9434E590B3136182', 'tax.business.attachment.list', '2017-06-02 16:02:32', NULL, NULL, '2017-06-02 16:02:32', '附件列表', 'app/tax/business/attachment/list', 'ED0720579B0E425F9B2109A0C35388A9');
------ 权限（管理员、分局执法人员、分局负责人、县局人员）
INSERT INTO `common-web-general`.`sys_role_action` (`role_id`, `action_id`) VALUES ('8DFEBE814BDD475A9E455D34500333E0', '05CB003C4A3E40E7BEA67AD8E5AFF6A2');
INSERT INTO `common-web-general`.`sys_role_action` (`role_id`, `action_id`) VALUES ('AAFDAF8B7B6F46D4AD18E7AFA578AA98', '05CB003C4A3E40E7BEA67AD8E5AFF6A2');
INSERT INTO `common-web-general`.`sys_role_action` (`role_id`, `action_id`) VALUES ('8DFEBE814BDD475A9E455D34500333E0', '85AD3C49171D4E26B878661966932447');
INSERT INTO `common-web-general`.`sys_role_action` (`role_id`, `action_id`) VALUES ('AAFDAF8B7B6F46D4AD18E7AFA578AA98', '85AD3C49171D4E26B878661966932447');
INSERT INTO `common-web-general`.`sys_role_action` (`role_id`, `action_id`) VALUES ('AAFDAF8B7B6F46D4AD18E7AFA578AA98', '93C5D9A36E34411DB480CA5CB56C546B');
INSERT INTO `common-web-general`.`sys_role_action` (`role_id`, `action_id`) VALUES ('E1051E05E4024667931522F5E213F80D', '93C5D9A36E34411DB480CA5CB56C546B');
INSERT INTO `common-web-general`.`sys_role_action` (`role_id`, `action_id`) VALUES ('8DFEBE814BDD475A9E455D34500333E0', '982E2172736B4836999F71048A1C6D24');
INSERT INTO `common-web-general`.`sys_role_action` (`role_id`, `action_id`) VALUES ('AAFDAF8B7B6F46D4AD18E7AFA578AA98', '982E2172736B4836999F71048A1C6D24');
INSERT INTO `common-web-general`.`sys_role_action` (`role_id`, `action_id`) VALUES ('FD526FA4908D4DF8B04FC4D9F258F9B3', '982E2172736B4836999F71048A1C6D24');
INSERT INTO `common-web-general`.`sys_role_action` (`role_id`, `action_id`) VALUES ('AAFDAF8B7B6F46D4AD18E7AFA578AA98', 'D00A50A694A04DAB841F87C739D1B272');
INSERT INTO `common-web-general`.`sys_role_action` (`role_id`, `action_id`) VALUES ('FD526FA4908D4DF8B04FC4D9F258F9B3', 'D00A50A694A04DAB841F87C739D1B272');
INSERT INTO `common-web-general`.`sys_role_action` (`role_id`, `action_id`) VALUES ('8DFEBE814BDD475A9E455D34500333E0', 'F10B35767692422F9434E590B3136182');
INSERT INTO `common-web-general`.`sys_role_action` (`role_id`, `action_id`) VALUES ('AAFDAF8B7B6F46D4AD18E7AFA578AA98', 'F10B35767692422F9434E590B3136182');
---- 附件上传
INSERT INTO `common-web-general`.`sys_action` (`id`, `code`, `create_time`, `description`, `method`, `modify_time`, `name`, `url`, `state_id`) VALUES ('C4540E0AAECA47DD96DDD9C014188F9E', 'tax.business.attachment.upload', '2017-05-31 16:00:20', NULL, NULL, '2017-05-31 16:00:20', '上传附件', 'app/tax/business/attachment/upload', '45FE5B6350BB4FE785AABC13DDED4133');
INSERT INTO `common-web-general`.`sys_action` (`id`, `code`, `create_time`, `description`, `method`, `modify_time`, `name`, `url`, `state_id`) VALUES ('E392A44C1F2142938480DC63D5F51543', 'tax.business.attachment.upload', '2017-06-02 17:05:51', NULL, NULL, '2017-06-02 17:05:51', '上传附件', 'app/tax/business/attachment/upload', '5A6E2DFC64884E04AF66F094389531B3');
------ 权限（管理员、分局执法人员）
INSERT INTO `common-web-general`.`sys_role_action` (`role_id`, `action_id`) VALUES ('8DFEBE814BDD475A9E455D34500333E0', 'C4540E0AAECA47DD96DDD9C014188F9E');
INSERT INTO `common-web-general`.`sys_role_action` (`role_id`, `action_id`) VALUES ('AAFDAF8B7B6F46D4AD18E7AFA578AA98', 'C4540E0AAECA47DD96DDD9C014188F9E');
INSERT INTO `common-web-general`.`sys_role_action` (`role_id`, `action_id`) VALUES ('8DFEBE814BDD475A9E455D34500333E0', 'E392A44C1F2142938480DC63D5F51543');
INSERT INTO `common-web-general`.`sys_role_action` (`role_id`, `action_id`) VALUES ('AAFDAF8B7B6F46D4AD18E7AFA578AA98', 'E392A44C1F2142938480DC63D5F51543');
-- 统计错详情
---- 详情
INSERT INTO `common-web-general`.`sys_action` (`id`, `code`, `create_time`, `description`, `method`, `modify_time`, `name`, `url`, `state_id`) VALUES ('1D8F3138134E4440A995AB47AD7D14D6', 'tax.business.examine.detail', '2017-05-30 22:53:12', NULL, NULL, '2017-05-30 22:53:12', '检查详情', 'app/tax/business/examine/detail', 'BB293ED529CE46138E992D852001DB0D');
INSERT INTO `common-web-general`.`sys_action` (`id`, `code`, `create_time`, `description`, `method`, `modify_time`, `name`, `url`, `state_id`) VALUES ('61CF01FAACFA4F60B93D8AA565C45693', 'tax.business.examine.detail', '2017-05-30 22:36:42', NULL, NULL, '2017-05-30 22:36:42', '检查详情', 'app/tax/business/examine/detail', 'F746C7B7F4154C3DA16A5F9A0EEA12AF');
INSERT INTO `common-web-general`.`sys_action` (`id`, `code`, `create_time`, `description`, `method`, `modify_time`, `name`, `url`, `state_id`) VALUES ('F9B3FAE21C13482E85CC6FABC5D6A1F2', 'tax.business.examine.detail', '2017-05-30 19:23:01', NULL, NULL, '2017-05-30 19:23:01', '检查详情', 'app/tax/business/examine/detail', '08F6DF520060436CB923C47039CCC16B');
------ 权限（管理员、分局执法人员、分局负责人、县局人员）
INSERT INTO `common-web-general`.`sys_role_action` (`role_id`, `action_id`) VALUES ('8DFEBE814BDD475A9E455D34500333E0', '1D8F3138134E4440A995AB47AD7D14D6');
INSERT INTO `common-web-general`.`sys_role_action` (`role_id`, `action_id`) VALUES ('AAFDAF8B7B6F46D4AD18E7AFA578AA98', '1D8F3138134E4440A995AB47AD7D14D6');
INSERT INTO `common-web-general`.`sys_role_action` (`role_id`, `action_id`) VALUES ('AAFDAF8B7B6F46D4AD18E7AFA578AA98', '61CF01FAACFA4F60B93D8AA565C45693');
INSERT INTO `common-web-general`.`sys_role_action` (`role_id`, `action_id`) VALUES ('FD526FA4908D4DF8B04FC4D9F258F9B3', '61CF01FAACFA4F60B93D8AA565C45693');
INSERT INTO `common-web-general`.`sys_role_action` (`role_id`, `action_id`) VALUES ('AAFDAF8B7B6F46D4AD18E7AFA578AA98', 'F9B3FAE21C13482E85CC6FABC5D6A1F2');
INSERT INTO `common-web-general`.`sys_role_action` (`role_id`, `action_id`) VALUES ('E1051E05E4024667931522F5E213F80D', 'F9B3FAE21C13482E85CC6FABC5D6A1F2');
---- 统计错误列表
INSERT INTO `common-web-general`.`sys_action` (`id`, `code`, `create_time`, `description`, `method`, `modify_time`, `name`, `url`, `state_id`) VALUES ('1B022E7D912C41B1A7423254E1A88F95', 'tax.business.paging.error', '2017-05-30 22:36:02', NULL, NULL, '2017-05-30 22:36:02', '获取错误业务列表', 'app/tax/business/paging/error', 'F746C7B7F4154C3DA16A5F9A0EEA12AF');
INSERT INTO `common-web-general`.`sys_action` (`id`, `code`, `create_time`, `description`, `method`, `modify_time`, `name`, `url`, `state_id`) VALUES ('A54B75E8E063409FA65D15BCF0DA6B60', 'tax.business.paging.error', '2017-05-30 17:25:41', NULL, NULL, '2017-05-30 17:25:41', '获取错误业务列表', 'app/tax/business/paging/error', '08F6DF520060436CB923C47039CCC16B');
INSERT INTO `common-web-general`.`sys_action` (`id`, `code`, `create_time`, `description`, `method`, `modify_time`, `name`, `url`, `state_id`) VALUES ('DAAE1D20A1F941CE811FD9153C6BBFE2', 'tax.business.paging.error', '2017-05-30 22:53:32', NULL, NULL, '2017-05-30 22:53:32', '获取错误业务列表', 'app/tax/business/paging/error', 'BB293ED529CE46138E992D852001DB0D');
------ 权限（管理员、分局执法人员、分局负责人、县局人员）
INSERT INTO `common-web-general`.`sys_role_action` (`role_id`, `action_id`) VALUES ('AAFDAF8B7B6F46D4AD18E7AFA578AA98', '1B022E7D912C41B1A7423254E1A88F95');
INSERT INTO `common-web-general`.`sys_role_action` (`role_id`, `action_id`) VALUES ('FD526FA4908D4DF8B04FC4D9F258F9B3', '1B022E7D912C41B1A7423254E1A88F95');
INSERT INTO `common-web-general`.`sys_role_action` (`role_id`, `action_id`) VALUES ('AAFDAF8B7B6F46D4AD18E7AFA578AA98', 'A54B75E8E063409FA65D15BCF0DA6B60');
INSERT INTO `common-web-general`.`sys_role_action` (`role_id`, `action_id`) VALUES ('E1051E05E4024667931522F5E213F80D', 'A54B75E8E063409FA65D15BCF0DA6B60');
INSERT INTO `common-web-general`.`sys_role_action` (`role_id`, `action_id`) VALUES ('8DFEBE814BDD475A9E455D34500333E0', 'DAAE1D20A1F941CE811FD9153C6BBFE2');
INSERT INTO `common-web-general`.`sys_role_action` (`role_id`, `action_id`) VALUES ('AAFDAF8B7B6F46D4AD18E7AFA578AA98', 'DAAE1D20A1F941CE811FD9153C6BBFE2');