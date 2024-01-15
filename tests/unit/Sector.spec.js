/**
 * Test for Sector Model in front end
 * @author Romello ten Broeke
 */
import Sector from '@/models/Sector'

let sectorMock
beforeEach(function () {
  sectorMock = new Sector(sectorsMock[0])
})

describe('Sector Model', () => {
  it('checks for instansce of a sector', () => {
    expect(sectorMock).toBeInstanceOf(Sector)
  })
  it('should give an error id is undefined', function () {
    expect(() => {
      // eslint-disable-next-line no-new
      new Sector({
        name: 'Fashion',
        description: 'Fashion Faculty'
      })
    }).toThrowError('id is undefined')
  })
  it('should give an error name is undefined', function () {
    expect(() => {
      // eslint-disable-next-line no-new
      new Sector({
        id: 3,
        description: 'Fashion Faculty'
      })
    }).toThrowError('name is undefined')
  })
  it('should give an error description is undefined', function () {
    expect(() => {
      // eslint-disable-next-line no-new
      new Sector({
        id: 3,
        name: 'Fashion'
      })
    }).toThrowError('description is undefined')
  })
})
const sectorsMock = [
  {
    id: 3,
    name: 'Fashion',
    description: 'Fashion Faculty'
  },
  {
    id: 4,
    name: 'ICT',
    description: 'ICT Faculty'
  },
  {
    id: 5,
    name: 'Business',
    description: 'Business Faculty'
  },
  {
    id: 6,
    name: 'Engineering',
    description: 'Engineering Faculty'
  },
  {
    id: 7,
    name: 'Health',
    description: 'Health Faculty'
  },
  {
    id: 8,
    name: 'Hospitality',
    description: 'Hospitality Faculty'
  },
  {
    id: 9,
    name: 'Media',
    description: 'Media Faculty'
  },
  {
    id: 10,
    name: 'Science',
    description: 'Science Faculty'
  },
  {
    id: 11,
    name: 'Sport',
    description: 'Sport Faculty'
  },
  {
    id: 12,
    name: 'Technology',
    description: 'Technology Faculty'
  }
]
