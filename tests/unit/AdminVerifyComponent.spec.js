import AdminVerifyComponent from '@/components/AdminDashboard/AdminVerifyComponent.vue'
import { mount } from '@vue/test-utils'

let wrapper
beforeEach(() => {
  wrapper = mount(AdminVerifyComponent,
    {
      global: {
        provide: {
          usersServices: mockUsersServices
        }
      }
    })
})

describe('Input fields', () => {
  it('input field exist', () => {
    const inputUsernameField = wrapper.find('input#verifyUsernameInput')
    const inputPasswordField = wrapper.find('input#verifyPasswordInput')
    expect(inputUsernameField.exists()).toBe(true)
    expect(inputPasswordField.exists()).toBe(true)
  })
})

describe('Buttons', () => {
  it('button exist', () => {
    const verifyButton = wrapper.find('button#checkPopupAdminVerify')
    const closeButton = wrapper.find('button#closePopupAdminVerify')
    expect(verifyButton.exists()).toBe(true)
    expect(closeButton.exists()).toBe(true)
  })
  it('should close the popup', async () => {
    let isPopupActive = AdminVerifyComponent.data().isAdmin = true
    const closeButton = wrapper.find('button#closePopupAdminVerify')
    await closeButton.trigger('click')
    await wrapper.vm.$nextTick()
    isPopupActive = false
    expect(isPopupActive).toBe(false)
  })
  it('should verify the user', async () => {
    let isAdmin = false
    await wrapper.find('input#verifyUsernameInput').setValue(mockUserData1.username)
    await wrapper.find('input#verifyPasswordInput').setValue(mockUserData1.password)
    const verifyButton = wrapper.find('button#checkPopupAdminVerify')
    await verifyButton.trigger('click')
    await wrapper.vm.$nextTick()
    isAdmin = mockUserData1.isAdmin
    expect(wrapper.vm.usernameInput, 'the value does not match with the input field for username')
      .toStrictEqual(mockUserData1.username)
    expect(wrapper.vm.passwordInput, 'the value does not match with the input field for username')
      .toStrictEqual(mockUserData1.password)
    expect(isAdmin, 'expected the user to be an admin').toBe(true)
  })
})

const mockUserData1 = {
  user_id: 101,
  sector_id: 1,
  first_name: 'Bas',
  last_name: 'Groote',
  email: 'BG@gmail.com',
  security_clearance: 1,
  password: 'bastiaan123',
  username: 'basgroote',
  bio: 'leeg',
  occupation: 'idk',
  date_of_birth: '2001-03-07',
  postalcode: null,
  img_path: null,
  isAdmin: true
}
const mockUserData2 = {
  user_id: 102,
  sector_id: 1,
  first_name: 'Arthur',
  last_name: 'Klein',
  email: 'AK@gmail.com',
  security_clearance: 1,
  password: 'arthur123',
  username: 'arthurklein',
  bio: 'leeg',
  occupation: 'idk',
  date_of_birth: '2001-03-07',
  postalcode: null,
  img_path: null,
  isAdmin: false
}

const mockUsers = [mockUserData1, mockUserData2]

const mockUsersServices = {
  asyncFindAll: jest.fn().mockResolvedValue(mockUsers),
  asyncSave: jest.fn()
}
