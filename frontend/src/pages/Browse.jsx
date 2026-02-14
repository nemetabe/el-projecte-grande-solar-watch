import React, { useEffect, useCallback } from 'react';
import { useSolarData } from '../hooks/data/useSolarData';
import { useSolarHistory } from '../hooks/data/useSolarHistory';
import { useSolarSave } from '../hooks/action/useSolarSave';
import { useCitySearch } from '../hooks/state/useCitySearch';

import BrowserForm from '../components/browser/BrowserForm';
import { SolarCard } from '../components/solar/SolarCard';
import { CityNameCard } from '../components/browser/CityNameCard';

import Stack from "@mui/material/Stack";
import { styled } from '@mui/material/styles';
import Paper from '@mui/material/Paper';
import Box from '@mui/material/Box';
import Skeleton from '@mui/material/Skeleton';

const Item = styled(Paper)(({ theme }) => ({
  backgroundColor: '#fff',
  ...theme.typography.body2,
  textAlign: 'center',
  background: 0,
  paperShadow: 0
}));

export default function Browse() {

    /*
    ─────────────────────────────
    DATA HOOKS
    ─────────────────────────────
    */

    const {
        solarTimes,
        isLoading: solarLoading,
        error: solarError,
        fetchSolarData,
    } = useSolarData();

    const {
        saveCity,
        saving: saveLoading,
        error: saveError,
        success: saveSuccess,
    } = useSolarSave();

    const {
        currentCity,
        currentDate,
        updateCity,
        updateDate,
    } = useCitySearch();

    const {
        history,
        addToHistory,
    } = useSolarHistory();

const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: '#fff',
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: 'center',
    color: (theme.vars ?? theme).palette.text.secondary,
    ...theme.applyStyles('dark', {
        backgroundColor: '#1A2027',
    }),
}));

    /*
    ─────────────────────────────
    FETCH SOLAR DATA WHEN CITY/DATE CHANGES
    ─────────────────────────────
    */

    useEffect(() => {

        if (!currentCity || !currentDate)
            return;

        fetchSolarData(
            currentDate,
            currentCity
        );

    }, [
        currentCity,
        currentDate,
        fetchSolarData,
    ]);


    /*
    ─────────────────────────────
    ADD TO HISTORY WHEN NEW RESULT ARRIVES
    ─────────────────────────────
    */

    useEffect(() => {

        if (!solarTimes)
            return;

        addToHistory(solarTimes);

    }, [
        solarTimes,
        addToHistory,
    ]);


    /*
    ─────────────────────────────
    SAVE HANDLER (CLEAN)
    ─────────────────────────────
    */

    const handleSave =
        useCallback(async (cityId) => {

            if (!solarTimes?.id)
                return;

            await saveCity(
                cityId,
                solarTimes.id
            );

        }, [
            solarTimes,
            saveCity,
        ]);


    /*
    ─────────────────────────────
    FORM SUBMIT HANDLER
    ─────────────────────────────
    */

    const handleSubmit =
        useCallback(() => {

            fetchSolarData(
                currentDate,
                currentCity
            );

        }, [
            currentCity,
            currentDate,
            fetchSolarData,
        ]);


    /*
    ─────────────────────────────
    RENDER
    ─────────────────────────────
    */

    return (

        <Stack
            direction="row"
            spacing={0}
            sx={{
                justifyContent: "space-between",
                alignItems: "flex-start",
                minWidth: 0,
                overflow: "hidden",
            }}
        >

            {/* ERROR DISPLAY */}

            <Item>

                <Box>

                    {solarError && (
                        <div className="text-red-400">
                            {solarError}
                        </div>
                    )}

                    {saveError && (
                        <div className="text-red-400">
                            Failed to save city
                        </div>
                    )}

                    {saveSuccess && (
                        <div className="text-green-400">
                            City saved
                        </div>
                    )}

                </Box>

            </Item>


            {/* SOLAR CARD */}

            <Item>

                <Box>

                    {solarTimes?.city?.id && (

                        <SolarCard

                            solarTimes={solarTimes}

                            solarCityId={
                                solarTimes.city.id
                            }

                            onSave={handleSave}

                            saving={saveLoading}

                        />

                    )}

                </Box>

            </Item>


            {/* SEARCH FORM */}

            <Item>

                <Box>

                    {solarLoading && (
                        <>
                            <Skeleton
                                variant="circular"
                                width={40}
                                height={40}
                            />

                            <Skeleton
                                variant="rounded"
                                width={120}
                                height={40}
                            />
                        </>
                    )}

                    <BrowserForm

                        currentCity={currentCity}

                        currentDate={currentDate}

                        onCityNameChange={updateCity}

                        onDateChange={updateDate}

                        onSubmit={handleSubmit}

                        isLoading={solarLoading}

                    />

                </Box>

            </Item>


            {/* HISTORY */}

            <Item sx={{ p: 4 }}>

                {history?.length > 0 ? (

                    <Stack
                        direction="column-reverse"
                        spacing={1}
                        sx={{
                            overflow: "auto"
                        }}
                    >

                        {history.map(entry => (

                            entry?.city && (

                                <CityNameCard

                                    key={
                                        `${entry.city.id}-${entry.date}`
                                    }

                                    city={
                                        entry.city.name
                                    }

                                    country={
                                        entry.city.country
                                    }

                                    date={
                                        entry.date
                                    }

                                />

                            )

                        ))}

                    </Stack>

                ) : (

                    <div>
                        No search history found.
                    </div>

                )}

            </Item>

        </Stack>
    );
}