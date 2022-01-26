import './App.css';
import React, { useState, useEffect } from 'react';
import Question from './element/Question';
import AnswerInput from './element/AnswerInput';
import AnswerTable from './element/AnswerTable';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import {
  getTodayQuestion, getTodayAnswer,
  createAnswer, updateAnswer, getAllAnswersByQuestionId
} from './RestAPI';

const useInput = (initialValue) => {
  const [value, setValue] = useState(initialValue);

  const onChange = (event) => {
    console.log(event.target);
    setValue(event.target.value);
  }

  return [{ value, onChange }, setValue]
}

export default function App() {
  const [questionEntity, setQuestion] = useState(undefined)
  const [answerEntity, setAnswerEntity] = useState(undefined)
  const [allAnswers, setAllAnswers] = useState(undefined)
  const [answerInput, setAnswerInputValue] = useInput("");

  const loadAnswer = async(questionId) => {
    let todayAnswerRes = await getTodayAnswer(questionId)      

    if(todayAnswerRes.status === 204) {
      return {id: 0, answer: ""}
    } else {
      return todayAnswerRes.data
    }
  }

  const loadQuestion = async() => {
    let todayQuestionRes = await getTodayQuestion()
    return todayQuestionRes.data
  }

  const loadAllAnswersByQuestionId = async(qId) => {
    let allAnswersRes = await getAllAnswersByQuestionId(qId)
    setAllAnswers(allAnswersRes.data)
  }

  const answerSubmit = async(e) => {
    e.preventDefault();

    let body = ({
      answer: answerInput.value,
      question: questionEntity
    })
        
    if(answerEntity.id === 0) {
      console.log(body)
      let res = await createAnswer(body)
      if(res.status === 201) {
        alert("저장 성공!");
        loadAllAnswersByQuestionId(questionEntity.id);
      } else alert("저장 실패!")
    } else {
      let res = await updateAnswer(answerEntity.id, body)
      if (res.status === 200) {
        alert("업데이트 성공!");
        loadAllAnswersByQuestionId(questionEntity.id);
      } else alert("업데이트 실패!")
    }
  }

  useEffect(() => {
    loadQuestion().then((q) => {
      setQuestion(q);
      loadAnswer(q.id).then((a) => {
        setAnswerEntity(a)
        setAnswerInputValue(a.answer);
      });
    });
    loadAllAnswersByQuestionId(questionEntity.id);
  }, []);

  return(
    <div className="App">
    <header className="App-header">
      <div sx={{ width: '600px'}}>
        <div sx={{ width: '100%' }}>
        <p>오늘의 질문!</p>
          { questionEntity 
            ? <Question questionEntity={questionEntity} />
            : "Question Loading..."
          }
          <Box
            component="form"
            onSubmit={answerSubmit}
            sx={{
              '& .MuiFilledInput-input': { 
                color: '#ffffff', m: 2, width:'50ch'
              },
            }}
          >
            { answerEntity 
              ? <AnswerInput 
                  useInput={answerInput}
                />
              : "Answer Loading..."          
            }
            <div>
              <Button
                  type="reset"
                  variant="outlined"
                  style={{ float: 'left' }}
              >Reset</Button>
              <Button
                type="submit"
                variant="contained"
                style={{ float: 'right' }}
              >Submit</Button>
              <Button
                type="cancel"
                variant="outlined"
                style={{ float: 'right' }}
              >Cancel</Button>
            </div>
          </Box>
          <AnswerTable data={allAnswers}/>
        </div>
      </div>
    </header>
    </div>
  );
}

