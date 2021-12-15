import axios from 'axios';

const ANSWER_API_PATH = '/answers'
const QUESTION_API_PATH = '/questions'

export const getAnswerById = async(answerId) => {
  return await axios.get(`${ANSWER_API_PATH}/${answerId}`)
    .then((res) => {
      return res
    });
}

export const createAnswer = async(body) => {
  return await axios.post(`${ANSWER_API_PATH}`, body)
    .then((res) => {
      return res
    })
}

export const updateAnswer = async(answerId, body) => {
  return await axios.put(`${ANSWER_API_PATH}/${answerId}`, body)
    .then((res) => {
      return res
    });
}

export const getTodayAnswer = async(questionId) => {
    return await axios.post(`${ANSWER_API_PATH}/${questionId}`)
      .then((res) => {
        return res
      });
}

export const getAllAnswers = async() => {
  return await axios.get(`${ANSWER_API_PATH}`)
    .then((res) => {
      return res
    })
}

export const getTodayQuestion = async() => {
  return await axios.get(`${QUESTION_API_PATH}/`)
    .then((res) => {
      return res
    })
}