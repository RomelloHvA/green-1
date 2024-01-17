import SdgIcon1 from '@/assets/SDG_Icons/SDG-Icons/E-WEB-Goal-01.png'
import SdgIcon2 from '@/assets/SDG_Icons/SDG-Icons/E-WEB-Goal-02.png'
import SdgIcon3 from '@/assets/SDG_Icons/SDG-Icons/E-WEB-Goal-03.png'
import SdgIcon4 from '@/assets/SDG_Icons/SDG-Icons/E-WEB-Goal-04.png'
import SdgIcon5 from '@/assets/SDG_Icons/SDG-Icons/E-WEB-Goal-05.png'
import SdgIcon6 from '@/assets/SDG_Icons/SDG-Icons/E-WEB-Goal-06.png'
import SdgIcon7 from '@/assets/SDG_Icons/SDG-Icons/E-WEB-Goal-07.png'
import SdgIcon8 from '@/assets/SDG_Icons/SDG-Icons/E-WEB-Goal-08.png'
import SdgIcon9 from '@/assets/SDG_Icons/SDG-Icons/E-WEB-Goal-09.png'
import SdgIcon10 from '@/assets/SDG_Icons/SDG-Icons/E-WEB-Goal-10.png'
import SdgIcon11 from '@/assets/SDG_Icons/SDG-Icons/E-WEB-Goal-11.png'
import SdgIcon12 from '@/assets/SDG_Icons/SDG-Icons/E-WEB-Goal-12.png'
import SdgIcon13 from '@/assets/SDG_Icons/SDG-Icons/E-WEB-Goal-13.png'
import SdgIcon14 from '@/assets/SDG_Icons/SDG-Icons/E-WEB-Goal-14.png'
import SdgIcon15 from '@/assets/SDG_Icons/SDG-Icons/E-WEB-Goal-15.png'
import SdgIcon16 from '@/assets/SDG_Icons/SDG-Icons/E-WEB-Goal-16.png'
import SdgIcon17 from '@/assets/SDG_Icons/SDG-Icons/E-WEB-Goal-17.png'
import SdgAction1 from '@/assets/SDG_Icons/SDG-action/E_SDG_action_card_square_1.jpg'
import SdgAction2 from '@/assets/SDG_Icons/SDG-action/E_SDG_action_card_square_2.jpg'
import SdgAction3 from '@/assets/SDG_Icons/SDG-action/E_SDG_action_card_square_3.jpg'
import SdgAction4 from '@/assets/SDG_Icons/SDG-action/E_SDG_action_card_square_4.jpg'
import SdgAction5 from '@/assets/SDG_Icons/SDG-action/E_SDG_action_card_square_5.jpg'
import SdgAction6 from '@/assets/SDG_Icons/SDG-action/E_SDG_action_card_square_6.jpg'
import SdgAction7 from '@/assets/SDG_Icons/SDG-action/E_SDG_action_card_square_7.jpg'
import SdgAction8 from '@/assets/SDG_Icons/SDG-action/E_SDG_action_card_square_8.jpg'
import SdgAction9 from '@/assets/SDG_Icons/SDG-action/E_SDG_action_card_square_9.jpg'
import SdgAction10 from '@/assets/SDG_Icons/SDG-action/E_SDG_action_card_square_10.jpg'
import SdgAction11 from '@/assets/SDG_Icons/SDG-action/E_SDG_action_card_square_11.jpg'
import SdgAction12 from '@/assets/SDG_Icons/SDG-action/E_SDG_action_card_square_12.jpg'
import SdgAction13 from '@/assets/SDG_Icons/SDG-action/E_SDG_action_card_square_13.jpg'
import SdgAction14 from '@/assets/SDG_Icons/SDG-action/E_SDG_action_card_square_14.jpg'
import SdgAction15 from '@/assets/SDG_Icons/SDG-action/E_SDG_action_card_square_15.jpg'
import SdgAction16 from '@/assets/SDG_Icons/SDG-action/E_SDG_action_card_square_16.jpg'
import SdgAction17 from '@/assets/SDG_Icons/SDG-action/E_SDG_action_card_square_17.jpg'

/**
Temporary file responsible for storing SDG goal information
 */
export class SustainableDevelopmentGoal {
  id;
  image;
  action;
  color;

  constructor (id, image, action, color) {
    this.id = id
    this.image = image
    this.action = action
    this.color = color
  }

  /**
   * return information based on the id
   * @param id of an SDG
   * @returns {{goal: SustainableDevelopmentGoal, info: *}} returns object with copy goal and additional info
   */
  static getInfo (id) {
    const infoObject = this.SdgInfo[id]
    return { goal: new SustainableDevelopmentGoal(id, this.SdgImages[id], this.SdgActions[id], this.Colors[id]), info: infoObject }
    // return correct goal according to id
  }

  /**
   * Function creates 17 goals based on the existing Sustainable Development Goals
   * and returns an array of goals.
   * @returns {*[]} array of SDG objects
   */
  static createListOfGoals () {
    const goalsArray = []
    for (let goalNumber = 1; goalNumber < 18; goalNumber++) {
      // new goal object
      const goal = new SustainableDevelopmentGoal(goalNumber,
        this.SdgImages[goalNumber], this.SdgActions[goalNumber], this.Colors[goalNumber])

      goalsArray.push(goal)
    }
    return goalsArray
  }

  /* ONLY TEMPORARY SDG DATA BELOW */
  static SdgInfo = {
    1: {
      title: 'No Poverty',
      problem: 'Poverty is a complex multidimensional problem with origins in both the national and international domains according to Agenda21 , it is rather difficult to find a universal solution to solve this problem globally, billions of people are starving, they lack the financial resources for the basic daily needs, whether they are men, women, or children, they all suffer from this crisis. In addition, this problem will only grow bigger because the majority of these people are just not well educated, they either do not have the money to send their kids to school, or the school just simply forbids a certain group of people from participating in class. Simply put, this is just a never-ending cycle and the problem will never stop growing.',
      targets: [
        'improving access to sustainable livelihoods, entrepreneurial opportunities, and productive resources',
        'providing universal access to basic social services',
        'progressively developing social protection systems to support those who cannot support themselves',
        'empowering people living in poverty and their organizations',
        'addressing the disproportionate impact of poverty on women',
        'intensifying international cooperation for poverty eradication'],
      potentialGoals: [
        'Providing an adequate level of education for children',
        'Distribution of clean water',
        'Ensuring basic health care',
        'Empowering women or girls'
      ]
    },
    2: {
      title: 'Zero Hunger',
      problem: 'According to the United Nations, little to no progress has been made in reducing anemia (A symptom that appears when you do not have enough red blood cells or hemoglobin that is cause by not eating enough or absorbing the adequate amount of iron for the human body) worldwide since 2000. It is estimated that in 2015 still roughly 2.8 billion people worldwide lack access to modern energy services and more than 1 billion do not have access to electricity. For the most part this grave development burden falls on rural areas, where a lack of access to modern energy services negatively affects productivity, educational attainment and even health and ultimately exacerbates the poverty trap. In addition, in rural areas, only 56% of births are attended by skilled health personnel, compared with 87% in urban areas. In fact, only about 16% of the rural population do not use improved drinking water sources, compared to 4% of the urban population. Lastly, about 50% of people living in rural areas lack improved sanitation facilities, compared to only 18% of people in urban areas.',
      targets: [
        'Increasing food productions in a sustainable way',
        'Enhancing food security',
        'Empowering small farmers',
        'Improving the agricultural industry'
      ],
      potentialGoals: [
        'Reducing the release of carbon dioxide for the transportation of agricultural products',
        'Ensuring the quality of the agricultural products',
        'Investing in the agricultural industry'
      ]
    },
    3: {
      title: 'Good Health and Well-being',
      problem: 'Health concerns are still remaining as a huge problem for third world countries, the death cases are also relatively higher. Most of those death cases can be prevented, such as: deaths of newborns and children; end the epidemics of AIDS, tuberculosis, malaria and other communicable diseases; mortality from non-communicable diseases; death from substance abuse; deaths and injuries from road traffic accidents; limited access to sexual and reproductive health-care services; no universal health coverage;  deaths and illnesses from hazardous chemicals and pollution.',
      targets: [
        'Enhancing the quality of the medication',
        'Eradicating the usage of illegal and dangerous drugs',
        'Providing more access to health-care services',
        'Reducing the pollution that is dangerous for the environment',
        'Enhancing the overall quality of traffic safety'
      ],
      potentialGoals: [
        'Ensuring basic health care',
        'Using a more sustainable source of energy',
        'Raising the overall awareness of the lethality of illegal and dangerous substance or drugs'
      ]
    },
    4: {
      title: 'Quality of Education',
      problem: 'The progress of achieving quality education around the world is slow and deficient, even before COVID-19. People from the Netherlands have access to quality, (nearly) free primary and secondary education because of a government spending of 43 billion Euros (45.9 U.S. dollars). However, this is not the case for countries with low or lower-middle-income countries. Low and lower-middle-income countries face an annual financing gap of 100 billion U.S. dollars.',
      targets: [
        'Boys and Girls have free, equitable and quality primary and secondary education',
        'Children have access to quality primary school preparation (childhood development)',
        'Equal access for men and women to affordable and quality higher education',
        'Increase number of youth and adults who have relevant skills (for employment, entrepreneurship)',
        'eliminate gender disparities in education and equal access to all levels of education for the vulnerable',
        'Ensure all youth and a substantial proportion of adults achieve literacy and numeracy',
        'Ensure all learners acquire the knowledge and skills needed to promote sustainable development',
        'Build and upgrade educational facilities with a safe and effective learning environment for all',
        'Expand globally the number of scholarships available to developing countries',
        'Increase the supply of qualified teachers '
      ],
      potentialGoals: [
        'Encourage scholar- and internships for students from low-income countries',
        'Teach English as a second language to low-level education areas',
        'Increase the number of quality teachers available',
        'Participate in an online teaching platform that puts focus on creating effective learning environments (online) in low-income countries'
      ]
    },
    5: {
      title: 'Gender Equality',
      problem: 'Gender equality is not a concept known around the world. Nearly half of married women lack decision-making power. The world is far from achieving global gender equality, with for example 1 in 5 young women being married before turning 18. In the Netherlands, women\'s rights and gender equality are integrated into aspects of its foreign policy.',
      targets: [
        'End all forms of discrimination against all women and girls everywhere',
        'Eliminate all forms of violence against girls, including trafficking, sexual and other types of exploitation',
        'Eliminate all harmful practices, such as child, early and forced marriage and female genital mutilation',
        'Provision of public services, infrastructure and social protection policies',
        'Ensure women\'s participation and equal opportunities for leadership on all levels',
        'Ensure universal access to sexual and reproductive health and reproductive rights',
        'Undertake reform to give women equal rights to economic resources',
        'Enhance use of enabling technology to promote the empowerment of women',
        'Adopt and strengthen strong policies and enforceable legislation for the promotion of gender equality'
      ],
      potentialGoals: [
        'Promotion of legal frameworks that enforce and monitor equality',
        'Promoting female leadership and participation by women',
        'Promoting (female) gender programmes',
        'Encourage (larger) workplace(s) to install water efficient sanitation systems, as well as unwasteful water use (i.e., dishwashing in restaurants)'
      ]
    },
    6: {
      title: 'Clean Water and Sanitation',
      problem: 'Billions of people around the world lack access to basic sustainable water structure. In addition, water pollution is also a concerning challenge since it affects both human health and the environment. All residents in the Netherlands benefit from clean and safe water, however, much action is needed to make this a global reality.',
      targets: [
        'achieve universal and equitable access to safe and affordable drinking water for all',
        'achieve access to adequate and equitable sanitation and hygiene for all and end open defecation',
        'improve water quality by reducing all kinds of pollution and substantially increase recycling and safe reuse globally',
        'increase water efficiency across all sectors and ensure sustainable water use to address water scarcity',
        'implement integrated water resource management at all levels, including through appropriate transboundary cooperation',
        'Protect and restore water-related ecosystems, such as mountains, forests, wetlands, lakes and more',
        'Expand combined international efforts to support developing countries in all water- and sanitation-related activities and programmes',
        'Support and strengthen the participation of improving local water and sanitation management'
      ],
      potentialGoals: [
        'Be(ing made) aware of water use and encourage use of sustainable water resources ',
        'Encouraging local communities to collaborate on improving water efficiency '
      ]
    },
    7: {
      title: 'Affordable and Clean Energy',
      problem: 'Access to affordable, reliable, sustainable and modern energy is not universal. Around the world many people still lack access to electricity and even more rely on fossil fuels. While the citizens of the Netherlands have widespread access to electricity most of that electricity comes from fossil fuels.',
      targets: [],
      potentialGoals: [
        'Improve energy efficiency in your field of work for example motion sensors for offices',
        'Encourage collaboration with local authority for clean energy projects',
        'Encourage clients for energy efficient products or services',
        'Create awareness for fossil fuel alternatives to promote clean energy',
        'Encourage policies against fossil fuels or energy inefficiency'
      ]
    },
    8: {
      title: 'Decent Work and Economic Growth',
      problem: '',
      targets: [],
      potentialGoals: [
        'Encourage sustainable business models and practices that not only focus on profit but also social and environmental impact',
        'Promote diversity and inclusion in the workplace ',
        'Promote for a greener economy for sustainable agriculture and renewable energy ',
        'Collaborate with small and medium sized companies to support their growth',
        'Encourage new labor laws and review old labor laws to check if they need to be updated',
        'International collaboration for sharing best practices and technologies'
      ]
    },
    9: {
      title: 'Industry, Innovation and Infrastructure',
      problem: '',
      targets: [],
      potentialGoals: [
        'Improve practices for energy-efficiency and waste reduction',
        'Encourage investment upgrading infrastructure to meet modern sustainability standards',
        'Collaborate on innovation and share knowledge',
        'Encourage R&D for sustainable technologies and practices'
      ]
    },
    10: {
      title: 'Reduced Inequalities',
      problem: '',
      targets: [],
      potentialGoals: [
        'Ensure that everyone\'s income improves, especially the poorest 40% so the wealth gap shrinks',
        'Give everyone a chance to participate in society, no matter their age, disability, race origin, religion, or wealth',
        'End discrimination so everyone has the same opportunities',
        'Creating and enforcing rules for taxes, wages and social support that reduces economic inequality',
        'Controlling global financial markets better to prevent rich people from getting richer at the expense of others',
        'Make sure that developing countries, especially the poorest ones, have a say in global economic decisions to make things fairer',
        'Help people move safely and responsibly, with good rules for migration, to address global inequalities and protect the rights of migrants',
        'Give special help to developing countries, especially the least developed ones, to help them grow and develop'
      ]
    },
    11: {
      title: 'Sustainable Cities and Communities',
      problem: '',
      targets: [],
      potentialGoals: [
        'Make cities welcoming and accessible for everyone, ensuring equal opportunities and services',
        'Create urban environments that clean, green and environmentally friendly',
        'Build cities and communities that are safe, resilient tot disasters and able to withstand challenges',
        'Ensure that housing is affordable and available to all residents',
        'Promote well-connected urban and rural areas with balanced development',
        'Promote health, well-being and active living for residents in cities and towns ',
        'Involve residents in decision-making for city planning and development',
        'Encourage sustainable economic growth that benefits all members of the community'
      ]
    },
    12: {
      title: 'Responsible Consumption and Production',
      problem: '',
      targets: [],
      potentialGoals: [
        'Reduce waste by recycling more',
        'Choose sustainable products for 50% of purchases',
        'Use less energy in buildings',
        'Cutting food waste',
        'Encourage people to carpool, bike or use public transportation',
        'Teaching about responsible consumption',
        'Switch to eco-friendly packaging materials',
        'Creating sustainable products',
        'Get employees involved in sustainability efforts'
      ]
    },
    13: {
      title: 'Climate Action',
      problem: '',
      targets: [],
      potentialGoals: [
        'Transition to renewable energy sources, such as solar and wind power, to reduce greenhouse gas emissions',
        'Implement energy-efficient practices in industries, transportation, and buildings to minimize carbon footprints',
        'Enhance climate education and awareness to encourage sustainable practices among citizens',
        'Develop and implement climate adaptation strategies to protect vulnerable communities and ecosystems',
        'Support international climate finance mechanisms to assist developing countries in their climate actions'
      ]
    },
    14: {
      title: ' Life Below Water',
      problem: '',
      targets: [],
      potentialGoals: [
        'Implement strict regulations on marine pollution, including plastic waste, in coastal and oceanic areas',
        'Promote sustainable fishing practices and protect marine biodiversity through marine protected areas',
        'Support research and monitoring of ocean acidification and its effects on marine ecosystems',
        'Collaborate with neighboring countries on transboundary marine conservation efforts',
        'Educate and engage communities in marine conservation efforts and sustainable fishing practices'
      ]
    },
    15: {
      title: 'Life On Land',
      problem: '',
      targets: [],
      potentialGoals: [
        'Support reforestation and afforestation projects both domestically and internationally',
        'Invest in sustainable land management practices to combat desertification and land degradation',
        'Contribute to global efforts to protect and conserve endangered species and their habitats',
        'Collaborate with other nations to address the threat of invasive alien species',
        'Promote the fair and equitable sharing of genetic resources and traditional knowledge'
      ]
    },
    16: {
      title: 'Peace, Justice and Strong Institutions',
      problem: '',
      targets: [],
      potentialGoals: []
    },
    17: {
      title: 'Partnerships For the Goals',
      problem: '',
      targets: [],
      potentialGoals: []
    }
  }

  static SdgImages = {
    1: SdgIcon1,
    2: SdgIcon2,
    3: SdgIcon3,
    4: SdgIcon4,
    5: SdgIcon5,
    6: SdgIcon6,
    7: SdgIcon7,
    8: SdgIcon8,
    9: SdgIcon9,
    10: SdgIcon10,
    11: SdgIcon11,
    12: SdgIcon12,
    13: SdgIcon13,
    14: SdgIcon14,
    15: SdgIcon15,
    16: SdgIcon16,
    17: SdgIcon17
  };

  static SdgActions = {
    1: SdgAction1,
    2: SdgAction2,
    3: SdgAction3,
    4: SdgAction4,
    5: SdgAction5,
    6: SdgAction6,
    7: SdgAction7,
    8: SdgAction8,
    9: SdgAction9,
    10: SdgAction10,
    11: SdgAction11,
    12: SdgAction12,
    13: SdgAction13,
    14: SdgAction14,
    15: SdgAction15,
    16: SdgAction16,
    17: SdgAction17
  };

  static Colors = {
    1: '#E5243B',
    2: '#DDA63A',
    3: '#4C9F38',
    4: '#C5192D',
    5: '#FF3A21',
    6: '#26BDE2',
    7: '#FCC30B',
    8: '#A21942',
    9: '#FD6925',
    10: '#DD1367',
    11: '#FD9D24',
    12: '#BF8B2E',
    13: '#3F7E44',
    14: '#0A98D9',
    15: '#56C02B',
    16: '#00689D',
    17: '#19486A'
  }
}
