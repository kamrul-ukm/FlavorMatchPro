export default {
  /**
   * 验证邮箱格式
   * @param {Rule} rule - 校验规则对象
   * @param {string} value - 输入的值
   * @param {Function} callback - 回调函数
   */
  validateEmail(rule, value, callback) {
    const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (!value) {
      callback(new Error("邮箱不能为空"));
    } else if (!emailPattern.test(value)) {
      callback(new Error("请输入正确的邮箱格式"));
    } else {
      callback();
    }
  },

  /**
   * 验证手机号格式
   * @param {Rule} rule - 校验规则对象
   * @param {string} value - 输入的值
   * @param {Function} callback - 回调函数
   */
  validatePhone(rule, value, callback) {
    const phonePattern = /^1[3-9]\d{9}$/; // 中国大陆手机号正则
    if (!value) {
      callback(new Error("手机号不能为空"));
    } else if (!phonePattern.test(value)) {
      callback(new Error("请输入正确的手机号"));
    } else {
      callback();
    }
  },
};
