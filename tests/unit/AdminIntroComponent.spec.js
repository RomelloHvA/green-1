import { mount } from '@vue/test-utils'
import AdminIntroComponent from '@/components/AdminDashboard/AdminIntroComponent.vue'
import AdminPanelComponent from '@/components/AdminDashboard/PanelComponent/AdminPanelComponent'

let wrapper

/**
 * Test for AdminIntroComponent.vue
 * Checks if the component renders correctly
 * @author Romello ten Broeke
 */

beforeEach(() => {
  wrapper = mount(AdminIntroComponent)
})

describe('IntroComponent.vue', () => {
  it('renders the correct text and contains AdminPanelComponents', () => {
    expect(wrapper.find('.card-title').text()).toBe('Welcome to the admin dashboard')
    expect(wrapper.find('.card-text').text()).toContain('On this page you will find all the activities an admin can do and a small explanation per activity')
  })
  it('should check the correct amount of adminPanelComponents', function () {
    const adminPanelComponents = wrapper.findAllComponents(AdminPanelComponent)
    expect(adminPanelComponents.length).toBe(3)
  })
  it('Check correct text and contains AdminPanelComponents with correct titles', () => {
    const adminPanelComponents = wrapper.findAllComponents(AdminPanelComponent)
    const titles = ['Users', 'Content', 'Quiz']
    expect(adminPanelComponents.length).toBeGreaterThan(0)

    for (let i = 0; i < adminPanelComponents.length; i++) {
      const actualTitle = adminPanelComponents.at(i).props('title')
      expect(actualTitle).toBe(titles[i])

      if (actualTitle === titles[i]) {
        console.log(`Title for AdminPanelComponent ${i + 1} is correct: ${actualTitle}`)
      } else {
        console.error(`Title for AdminPanelComponent ${i + 1} is incorrect. Expected: ${titles[i]}, Actual: ${actualTitle}`)
      }
    }
  })
})
