import TextField from '@mui/material/TextField';
import React, { useState, useEffect, useCallback } from 'react';

export default function AnswerInput( { useInput } ) {

  return (
    <TextField
      required
      id="outlined-answer-input"
      label="Answer"
      fullWidth
      variant="filled"
      {...useInput}
      multiline
      rows={5}
      inputProps={{
        maxLength: 200,
      }}
    />
	)
}