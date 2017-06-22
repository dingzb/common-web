-- 增加业务类型到三个统计
INSERT INTO `tax`.`sys_action` (`id`, `code`, `create_time`, `description`, `method`, `modify_time`, `name`, `url`, `state_id`) VALUES ('127263BB2A9C4CAE9CEAC5EC7DCDF081', 'tax.business.category.list.all', '2017-06-22 22:04:55', NULL, NULL, '2017-06-22 22:04:55', '获取所有业务类型', 'app/tax/business/category/list/all', 'BB293ED529CE46138E992D852001DB0D');
INSERT INTO `tax`.`sys_action` (`id`, `code`, `create_time`, `description`, `method`, `modify_time`, `name`, `url`, `state_id`) VALUES ('1FF8232757DD430CAEA34E096479EE46', 'tax.business.category.list.all', '2017-06-22 22:04:26', NULL, NULL, '2017-06-22 22:04:26', '获取所有业务类型', 'app/tax/business/category/list/all', 'F746C7B7F4154C3DA16A5F9A0EEA12AF');
INSERT INTO `tax`.`sys_action` (`id`, `code`, `create_time`, `description`, `method`, `modify_time`, `name`, `url`, `state_id`) VALUES ('508A6329E7D34E8EA71062CF0A2E4FF5', 'tax.business.category.list.all', '2017-06-22 16:24:38', NULL, NULL, '2017-06-22 16:24:38', '获取所有业务类型', 'app/tax/business/category/list/all', '08F6DF520060436CB923C47039CCC16B');
-- 统计增加权限
INSERT INTO `tax`.`sys_role_action` (`role_id`, `action_id`) VALUES ('AAFDAF8B7B6F46D4AD18E7AFA578AA98', '127263BB2A9C4CAE9CEAC5EC7DCDF081');
INSERT INTO `tax`.`sys_role_action` (`role_id`, `action_id`) VALUES ('AAFDAF8B7B6F46D4AD18E7AFA578AA98', '1FF8232757DD430CAEA34E096479EE46');
INSERT INTO `tax`.`sys_role_action` (`role_id`, `action_id`) VALUES ('AAFDAF8B7B6F46D4AD18E7AFA578AA98', '508A6329E7D34E8EA71062CF0A2E4FF5');
INSERT INTO `tax`.`sys_role_action` (`role_id`, `action_id`) VALUES ('8DFEBE814BDD475A9E455D34500333E0', '127263BB2A9C4CAE9CEAC5EC7DCDF081');
INSERT INTO `tax`.`sys_role_action` (`role_id`, `action_id`) VALUES ('E1051E05E4024667931522F5E213F80D', '508A6329E7D34E8EA71062CF0A2E4FF5');
INSERT INTO `tax`.`sys_role_action` (`role_id`, `action_id`) VALUES ('FD526FA4908D4DF8B04FC4D9F258F9B3', '1FF8232757DD430CAEA34E096479EE46');


-- 查询业务增加 类型选择
INSERT INTO `tax`.`sys_action` (`id`, `code`, `create_time`, `description`, `method`, `modify_time`, `name`, `url`, `state_id`) VALUES ('C6CB276D88A8483D9AA07F42DDC854A4', 'tax.business.category.list', '2017-06-22 22:30:49', NULL, NULL, '2017-06-22 22:30:49', '业务项目列表', 'app/tax/business/category/list', '85D85C3210184D21B7B0949FE34756AB');
INSERT INTO `tax`.`sys_action` (`id`, `code`, `create_time`, `description`, `method`, `modify_time`, `name`, `url`, `state_id`) VALUES ('E3A12122ED18482DB3E451791E8BCBED', 'tax.business.category.type.list', '2017-06-22 22:30:23', NULL, NULL, '2017-06-22 22:30:23', '业务类型分组列表', 'app/tax/business/category/type/list', '85D85C3210184D21B7B0949FE34756AB');
-- 权限
INSERT INTO `tax`.`sys_role_action` (`role_id`, `action_id`) VALUES ('8DFEBE814BDD475A9E455D34500333E0', 'C6CB276D88A8483D9AA07F42DDC854A4');
INSERT INTO `tax`.`sys_role_action` (`role_id`, `action_id`) VALUES ('AAFDAF8B7B6F46D4AD18E7AFA578AA98', 'C6CB276D88A8483D9AA07F42DDC854A4');
INSERT INTO `tax`.`sys_role_action` (`role_id`, `action_id`) VALUES ('E1051E05E4024667931522F5E213F80D', 'C6CB276D88A8483D9AA07F42DDC854A4');
INSERT INTO `tax`.`sys_role_action` (`role_id`, `action_id`) VALUES ('FD526FA4908D4DF8B04FC4D9F258F9B3', 'C6CB276D88A8483D9AA07F42DDC854A4');
INSERT INTO `tax`.`sys_role_action` (`role_id`, `action_id`) VALUES ('8DFEBE814BDD475A9E455D34500333E0', 'E3A12122ED18482DB3E451791E8BCBED');
INSERT INTO `tax`.`sys_role_action` (`role_id`, `action_id`) VALUES ('AAFDAF8B7B6F46D4AD18E7AFA578AA98', 'E3A12122ED18482DB3E451791E8BCBED');
INSERT INTO `tax`.`sys_role_action` (`role_id`, `action_id`) VALUES ('E1051E05E4024667931522F5E213F80D', 'E3A12122ED18482DB3E451791E8BCBED');
INSERT INTO `tax`.`sys_role_action` (`role_id`, `action_id`) VALUES ('FD526FA4908D4DF8B04FC4D9F258F9B3', 'E3A12122ED18482DB3E451791E8BCBED');
-- 补充县局人员 查询模块 附件查看
INSERT INTO `tax`.`sys_role_action` (`role_id`, `action_id`) VALUES ('E1051E05E4024667931522F5E213F80D', '982E2172736B4836999F71048A1C6D24');
