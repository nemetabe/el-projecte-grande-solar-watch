const parseTime = (timeStr) => {
  const [time, ampm] = timeStr.split(' ');
  let [hours, minutes, seconds] = time.split(':').map(Number);
  if (ampm === 'PM' && hours !== 12) {
    hours += 12;
  }
  if (ampm === 'AM' && hours === 12) {
    hours = 0; // Midnight
  }
  return hours * 3600 + minutes * 60 + seconds;
};

const timePeriods = [
  { name: 'Night Sky', key: 'nightSky', startHourSeconds: parseTime("10:36:06 PM") }, // last_light to first_light
  { name: 'Pre-Dawn / Deep Dawn', key: 'deepDawn', startHourSeconds: parseTime("3:44:44 AM") }, // first_light
  { name: 'Early Sunrise / Pre-Sunrise Glow', key: 'earlySunrise', startHourSeconds: parseTime("5:11:38 AM") }, // dawn
  { name: 'Morning / Bright Sky', key: 'morningBright', startHourSeconds: parseTime("5:43:42 AM") }, // sunrise
  { name: 'Midday / Afternoon', key: 'middayAfternoon', startHourSeconds: parseTime("1:10:25 PM") }, // solar_noon
  { name: 'Late Afternoon / Golden Hour Sunset', key: 'goldenSunset', startHourSeconds: parseTime("7:57:12 PM") }, // golden_hour
  { name: 'Sunset / Early Twilight', key: 'earlyTwilight', startHourSeconds: parseTime("8:37:08 PM") }, // sunset
  { name: 'Deep Twilight / Fading Light', key: 'deepTwilight', startHourSeconds: parseTime("9:09:13 PM") }, // dusk
];

const skyGradients = {
  'deepDawn': {
    name: 'Pre-Dawn - Mystical Violet',
    key: 'deepDawn',
    gradientCss: 'linear-gradient(to top, #0A0A2A 0%, #2C0F4A 40%, #4D2E68 100%)', // Deep dark blue to deep violet
    textColor: '#E6E6FA', // Light lavender for contrast against dark background
  },
  'earlySunrise': {
    name: 'Early Sunrise - Soft Peach Glow',
    key: 'earlySunrise',
    gradientCss: 'linear-gradient(to top, #FFD1DC 0%, #FFB6C1 50%, #FF8C69 100%)', // Soft pink to warm coral
    textColor: '#8B0000', // Dark red for contrast against light peachy background
  },
  'morningBright': {
    name: 'Morning - Clear Bright Sky',
    key: 'morningBright',
    gradientCss: 'linear-gradient(to top, #F0F8FF 0%, #87CEEB 50%, #FFD700 100%)', // Light blue-white to sky blue to golden yellow
    textColor: '#2F4F4F', // Dark slate gray for contrast against bright background
  },
  'middayAfternoon': {
    name: 'Midday - Serene Blue',
    key: 'middayAfternoon',
    gradientCss: 'linear-gradient(to top, #ADD8E6 0%, #AEC6CF 50%, #B0E0E6 100%)', // Soft sky blue to muted blue-gray
    textColor: '#2F4F4F', // Dark slate gray for contrast against light blue background
  },
  'goldenSunset': {
    name: 'Golden Hour - Warm Embrace',
    key: 'goldenSunset',
    gradientCss: 'linear-gradient(to top, #FFD700 0%, #FFA07A 50%, #CD5C5C 100%)', // Golden yellow to salmon to indian red
    textColor: '#8B0000', // Dark red for contrast against warm background
  },
  'earlyTwilight': {
    name: 'Early Twilight - Blushing Sky',
    key: 'earlyTwilight',
    gradientCss: 'linear-gradient(to top, #FF6347 0%, #C71585 50%, #4B0082 100%)', // Tomato red to fuchsia to indigo
    textColor: '#F5F5DC', // Beige for contrast against vibrant background
  },
  'deepTwilight': {
    name: 'Deep Twilight - Velvet Purple',
    key: 'deepTwilight',
    gradientCss: 'linear-gradient(to top, #483D8B 0%, #2F4F4F 50%, #1A1A2E 100%)', // Dark slate blue to dark slate gray to deep navy
    textColor: '#E6E6FA', // Light lavender for contrast against dark background
  },
  'nightSky': {
    name: 'Night - Tranquil Deep Blue',
    key: 'nightSky',
    gradientCss: 'linear-gradient(to top, #000033 0%, #000080 50%, #191970 100%)', // Very dark blue to medium blue to midnight blue
    textColor: '#F0F8FF', // Alice blue for contrast against dark background
  },
};

export { timePeriods, skyGradients };