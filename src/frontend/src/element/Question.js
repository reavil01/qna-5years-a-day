import React from 'react';
import Box from '@mui/material/Box';

export default function Question({ questionEntity }) {
	const { sentence } = questionEntity

	return (
		<Box component="div"
			sx={{
				display: 'block',
				mb: 1,
				p: 1,
				backgroundColor: '#303540',
				whiteSpace: 'normal',
			}}
		>
			{sentence}
		</Box>
	);
}