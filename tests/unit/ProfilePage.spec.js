import ProfilePage from '@/components/profile/ProfilePage'
import { mount } from '@vue/test-utils'

/**
 * Test for ProfilePage
 * @author Jiaming Yan
 */
const user = {
  user_id: 2,
  sector_id: 1,
  first_name: 'Hans',
  last_name: 'Groot',
  email: 'h@test.nl',
  security_clearance: 1,
  password: 'hans',
  username: 'hansmans',
  bio: 'leeg',
  occupation: 'idk',
  date_of_birth: '2001-03-07',
  postalcode: null,
  user_goal: null,
  actionplans: [],
  img_path: null,
  isAdmin: true
}
let wrapper
jest.mock('@/components/profile/ProfileActionPlans', () => ({
  name: 'MockProfileActionPlans',
  template: '<div>Mock ProfileActionPlans Component</div>'
}))
const mockUsersServices = {
  asyncFindById: jest.fn().mockResolvedValue(user)
}

const mockSessionService = {
  currentAccount: Promise.resolve({ user_id: user.user_id }) // Mock current account data
}

beforeEach(async () => {
  wrapper = mount(ProfilePage, {
    global: {
      provide: {
        usersServices: mockUsersServices,
        sessionService: mockSessionService
      }
    }
  })
})
describe('Mounting the component...', () => {
  it('should have a profile variable', async () => {
    expect(wrapper.vm.profile, 'profile should exist').toBeTruthy()
  })
  it('should clone the user immediately', async () => {
    expect(wrapper.vm.profile, 'profile should exist').toStrictEqual(user)
    expect(wrapper.vm.oldProfileData, 'oldProfileDate should be exactly the same').toStrictEqual(wrapper.vm.profile)
  })
  it('should display the correct values of the current user', async () => {
    expect(wrapper.find('input#inputFirstName').element.value, 'first name should be the same').toStrictEqual(user.first_name)
    expect(wrapper.find('input#inputLastName').element.value, 'last name should be the same').toStrictEqual(user.last_name)
    expect(wrapper.find('input#inputUserName').element.value, 'username should be the same').toStrictEqual(user.username)
    expect(wrapper.find('input#inputBirthday').element.value, 'date of birth should be the same').toStrictEqual(user.date_of_birth)
    expect(wrapper.find('input#inputOccupation').element.value, 'occupation should be the same').toStrictEqual(user.occupation)
    expect(wrapper.find('textarea#inputBio').element.value, 'bio should be the same').toStrictEqual(user.bio)
  })
})
describe('The buttons', () => {
  it('should exist', () => {
    expect(wrapper.find('button#save-btn'), 'save button should exist').toBeTruthy()
    expect(wrapper.find('button#cancel-btn'), 'cancel button should exist').toBeTruthy()
  })
  it('should be disabled when no changes are made', () => {
    expect(wrapper.find('button#save-btn').isDisabled(), 'save button should be disabled').toBeTruthy()
    expect(wrapper.find('button#cancel-btn').isDisabled(), 'cancel button should be disabled').toBeTruthy()
  })
  it('should be enabled when something is changed', async () => {
    await wrapper.find('input#inputFirstName').setValue('Joey')
    expect(wrapper.find('button#save-btn').isDisabled(), 'save button should be disabled').not.toBeTruthy()
    expect(wrapper.find('button#cancel-btn').isDisabled(), 'cancel button should be disabled').not.toBeTruthy()
  })
  it('should revert all changes when cancel is clicked', async () => {
    const windowConfirm = window.confirm
    window.confirm = jest.fn(() => true)
    await wrapper.find('input#inputFirstName').setValue('Joey')
    await wrapper.find('button.editButton[type="button"]').trigger('click')
    await wrapper.vm.$nextTick()
    expect(wrapper.vm.profile, 'all changes should be reverted').toStrictEqual(user)
    window.confirm = windowConfirm
  })
})
