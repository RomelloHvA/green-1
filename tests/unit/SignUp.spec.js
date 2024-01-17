import { mount } from '@vue/test-utils'
import SignUpValidationView from '@/views/SignUpValidationView'

/**
 * Test for Sign up
 * @author Santosh Kakkar
 */
let wrapper

beforeEach(() => {
  const mockUsersServices = {
    asyncSave: jest.fn()
  }
  wrapper = mount(SignUpValidationView, {
    // Providing mockUserService as injection
    global: {
      provide: {
        usersServices: mockUsersServices
      }
    }
  })
})

describe('Sign Up fields', () => {
  it('Should have input fields', () => {
    expect(wrapper.find('#firstName').exists(),
      'Cannot find input field for firstName').toBe(true)
    expect(wrapper.find('#lastName').exists(),
      'Cannot find input field for lastName').toBe(true)
    expect(wrapper.find('#emailBox').exists(),
      'Cannot find input field for email').toBe(true)
    expect(wrapper.find('#sectors').exists(),
      'Cannot find input field for faculty').toBe(true)
    expect(wrapper.find('#postCodeBox').exists(),
      'Cannot find input field for postCode').toBe(true)
    expect(wrapper.find('#usernameBox').exists(),
      'Cannot find input field for username').toBe(true)
    expect(wrapper.find('#passwordBox').exists(),
      'Cannot find input field for password field').toBe(true)
    expect(wrapper.find('#confirmPasswordBox').exists(),
      'Cannot find input field for confirm password').toBe(true)
    expect(wrapper.find('#dateOfBirthBox').exists(),
      'Cannot find input field for date').toBe(true)
  })
})
describe('Sign up button', () => {
  it('Should have signup button', () => {
    expect(wrapper.find('#signUpBtn'), 'no sign up button found ').toBeTruthy()
  })
  it('should be disabled', () => {
    expect(wrapper.find('#signUpBtn').isDisabled(), 'the button is not disabled').toBeTruthy()
  })
})
