import { ActionPlansAdaptor } from '@/services/ActionPlansAdaptor'
import CONFIG from '@/app-config'
import { enableFetchMocks } from 'jest-fetch-mock'

enableFetchMocks()

const actionPlanService = new ActionPlansAdaptor(CONFIG.BACKEND_URL + '/actionplans')

beforeEach(() => {
  fetch.resetMocks()
})

/**
 * Test actionPlanService
 * @author Romello ten Broeke
 */

describe('Method findActionPlanBySectorId', () => {
  it('should find actionPlan by Sector Id', function () {
    fetch.mockResponseOnce(JSON.stringify(mockActionPlans[0]))
    actionPlanService.findActionPlanBySectorId(2).then((response) => {
      expect(response.editableActionPlans.value).toEqual(mockActionPlans[0])
      expect(response.isPending.value).toEqual(false)
      expect(response.error.value).toEqual(null)
    })
  })
  it('should not find any actionPlan', function () {
    fetch.mockResponseOnce(JSON.stringify([]))
    actionPlanService.findActionPlanBySectorId(7).then((response) => {
      expect(response.editableActionPlans.value).toEqual([])
      expect(response.isPending.value).toEqual(false)
      expect(response.error.value).toEqual(null)
    })
  })
})

const mockActionPlans = [{
  id: 3,
  title: 'Enhance Global Education Awareness',
  description: '  <strong>Objective:</strong> To increase awareness and support for SDG 4, Quality Education, among a broad audience.<br><strong>Actions:</strong><br>* Research and share information about global education challenges and successes.<br>* Organize or participate in educational events or webinars focusing on SDG 4.<br>* Volunteer with organizations that focus on improving education quality worldwide.<br>* Advocate for educational policies that align with SDG 4 objectives.<br>* Collaborate with educational institutions to develop SDG 4-focused programs.\n',
  sector: {
    id: 2,
    name: 'Inshallah',
    description: 'No Shallah assigned'
  },
  sdgArray: [
    4,
    5,
    7,
    10
  ]
},
{
  id: 52,
  title: 'Advocating for Good Health',
  description: '<strong>Goal:</strong> Promote well-being for all ages. <br><strong>Actions:</strong><ul><li>Participate in community health awareness campaigns.</li><li>Organize fitness and wellness activities in your community.</li><li>Support mental health initiatives through volunteering or advocacy.</li><li>Collaborate with local health organizations for health drives or education sessions.</li></ul>',
  sector: {
    id: 1,
    name: 'None',
    description: 'No sector assigned'
  },
  sdgArray: [
    3
  ]
}
]
