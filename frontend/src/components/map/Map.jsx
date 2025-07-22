import React, { useState, useRef, useEffect } from 'react';
import * as maptilersdk from '@maptiler/sdk';
import { GeocodingControl } from "@maptiler/geocoding-control/react";
import "@maptiler/geocoding-control/style.css";
import "@maptiler/sdk/dist/maptiler-sdk.css";
import configData from "../../config";
import "./map.css";


function Map({pos, onPosChange}) {
  const mapContainer = useRef(null);
  const mapController = useRef(null);
  const map = useRef(null);
  const markerRef = useRef(null);
  const [current, setCurrent] = useState({
    lng: pos.lng,
    lat: pos.lat
  });
  const zoom = 14;
  maptilersdk.config.apiKey = configData.MAPTILER_API_KEY;

  useEffect(() => {
    if (map.current) return;

    map.current = new maptilersdk.Map({
      container: mapContainer.current,
      style: maptilersdk.MapStyle.STREETS,
      center: [current.lng, current.lat],
      language: maptilersdk.Language.ENGLISH,
      zoom: zoom,
    });

    const marker = new maptilersdk.Marker({ color: "#FF0000", draggable: true })
      .setLngLat([current.lng, current.lat])
      .addTo(map.current);

    markerRef.current = marker;

    marker.on('dragend', () => {
      const lngLat = marker.getLngLat().wrap();
      setCurrent({ lng: lngLat.lng, lat: lngLat.lat });
      onPosChange({ lng: lngLat.lng, lat: lngLat.lat });
    });

    // const gc =  new maptilersdk.GeocodingControl({
    //   apiKey: maptilersdk.config.apiKey,
    //   limit: 3,
    // });
    
    // mapController.current = gc;
    // map.current.addControl(mapController.current);

    // gc.on('select', (result) => {
    //   if (!result || !result.center) return;
    //   const [lng, lat] = result.center;
    //   setCurrent({ lng, lat });
    //   onPosChange({ lng, lat });

    //   // Move marker to new location
    //   if (markerRef.current) {
    //     markerRef.current.setLngLat([lng, lat]);
    //   }
    //   // Fly map to new location
    //   map.current.flyTo({ center: [lng, lat], zoom });
    // });

  }, []);

  return (
    <div>
      <div className="map-wrap">
        {/* <div className="geocoding" ref={mapController}> 
        </div> */}
        <div ref={mapContainer} id='map' className="map" />
      </div>
      <h4 className="text-black text-1l">
        lat:{current.lat} <br></br>
        lng:{current.lng}
      </h4>
    </div>
  );
}

export default Map;