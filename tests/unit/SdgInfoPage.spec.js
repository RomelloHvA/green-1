import SdgInfoPage from '@/components/LandingPage/SdgInfoPage.vue'
import { mount } from '@vue/test-utils'

let wrapper

const router = {
  push: jest.fn(),
  routes: [
    {
      path: '/sdg/:id',
      name: 'sdg',
      component: SdgInfoPage
    }
  ]
}

beforeEach(() => {
  wrapper = mount(SdgInfoPage, {
    global: {
      mocks: {
        $route: {
          params: {
            id: 1
          }
        }
      }
    }
  })
  wrapper.vm.$router = router
})

/**
 * Test SdgInfoPage.vue
 * @Author Justin Chan
 */
describe('SdgInfoPage.vue', () => {
  it('renders a sdg correctly', async () => {
    wrapper.vm.$router.push('/sdg/1')
    await wrapper.vm.$nextTick()
    const currentUrl = wrapper.vm.$route.fullPath = '/sdg/1'
    expect(currentUrl).toBe('/sdg/1')
  })
})
describe('The buttons', () => {
  it('should have a button no navigate to the previous sdg', () => {
    expect(wrapper.find('button#previousSdgButton'), 'button to previous sdg').toBeTruthy()
  })
  it('should navigate to SDG 17', async () => {
    const button = wrapper.find('button#previousSdgButton[type="button"]')
    await button.trigger('click')
    await wrapper.vm.$router.push('/sdg/17')
    await wrapper.vm.$nextTick()
    const currentUrl = wrapper.vm.$route.fullPath = '/sdg/17'
    expect(currentUrl).toBe('/sdg/17')
  })
  it('should have a button no navigate to the next sdg', () => {
    expect(wrapper.find('button#nextSdgButton'), 'button to next sdg').toBeTruthy()
  })
  it('should navigate to SDG 2', async () => {
    const button = wrapper.find('button#previousSdgButton[type="button"]')
    await button.trigger('click')
    await wrapper.vm.$router.push('/sdg/2')
    await wrapper.vm.$nextTick()
    const currentUrl = wrapper.vm.$route.fullPath = '/sdg/2'
    expect(currentUrl).toBe('/sdg/2')
  })
})
