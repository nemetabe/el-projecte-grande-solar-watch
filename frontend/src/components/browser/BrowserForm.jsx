import React, { useState, useEffect } from "react";
import { useMyRef } from "../../hooks/utils/useMyRef";
import { usePrevious } from "../../hooks/utils/usePrevious";
import DateSelector from "./DateSelector";
import FormInput from "../common/FormInput";

function BrowserForm({
  currentCity,
  onCityNameChange,
  currentDate,
  onDateChange,
  onSubmit,
  isLoading
}) {
  const previousCity = usePrevious(currentCity);
  const [localCity, setLocalCity] = useState(currentCity);

  const cityInputRef = useMyRef(null, (el) => {
    el.focus();
    console.log("City input auto-focused");
  });

  useEffect(() => {
    setLocalCity(currentCity);
  }, [currentCity]);

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!localCity?.trim()) {
      alert("Please enter a city name");
      return;
    }

    if (!currentDate) {
      alert("Please select a valid date");
      return;
    }

    onCityNameChange(localCity);
    onSubmit();
  };

  return (
    <div>
      <h3>Select a city</h3>

      <FormInput
        ref={cityInputRef}
        id="cityName"
        name="cityName"
        value={localCity}
        onChange={(e) => setLocalCity(e.target.value)}
        required
      />

      {previousCity && previousCity !== currentCity && (
        <small style={{ color: "#666", fontSize: "12px" }}>
          Previously: {previousCity}
        </small>
      )}

      <DateSelector date={currentDate} onDateChange={onDateChange} />

      <button
        disabled={isLoading}
        onClick={handleSubmit}
        className="bg-blue-600 hover:bg-blue-700 disabled:bg-gray-500 text-white px-6 py-2 rounded"
      >
        {isLoading ? "Loading..." : "Let's go!"}
      </button>
    </div>
  );
}

export default BrowserForm;