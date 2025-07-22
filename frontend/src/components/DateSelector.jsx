import React, { useState } from 'react';
import dayjs from 'dayjs';
import { Box } from '@mui/material';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';

function DateSelector({ date, onDateChange }) {
  const [selectedDate, setSelectedDate] = useState(dayjs(date));

  const handleDateChange = (newDate) => {
    setSelectedDate(newDate);
    onDateChange(newDate);
  };

  return (
    <div>
      <LocalizationProvider dateAdapter={AdapterDayjs}>
        <Box>
          <DatePicker
            label="Select date"
            sx={{
              width: '100%',
              display: 'flex',
              justifyContent: 'center',
              position: 'relative',
            }}
            value={selectedDate}
            onChange={handleDateChange}
            slotProps={{
              field: {
                clearable: true,
              },
            }}
          />
        </Box>
      </LocalizationProvider>
      {/* <h4 className=" .glass text-black text-1l">
        date: {selectedDate ? selectedDate.format('YYYY-MM-DD') : 'None'}
      </h4> */}
    </div>
  );
}

export default DateSelector;