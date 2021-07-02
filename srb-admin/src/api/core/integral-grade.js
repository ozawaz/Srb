import request from '@/utils/request'

export default {
  // 获取所有积分等级信息
  list() {
    return request({
      url: '/admin/core/integralGrade/list',
      method: 'get'
    })
  },
  // 根据积分id删除积分等级
  removeById(id) {
    return request({
      url: `/admin/core/integralGrade/remove/${id}`,
      method: 'delete'
    })
  },
  // 保存积分等级
  save(integralGrade) {
    return request({
      url: '/admin/core/integralGrade/save',
      method: 'post',
      data: integralGrade
    })
  },
  // 根据id查找积分等级
  getById(id) {
    return request({
      url: `/admin/core/integralGrade/get/${id}`,
      method: 'get'
    })
  },
  // 更新积分等级
  updateById(integralGrade) {
    return request({
      url: '/admin/core/integralGrade/update',
      method: 'put',
      data: integralGrade
    })
  }
}
