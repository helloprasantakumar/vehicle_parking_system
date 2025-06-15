import React from 'react';
import ParkingSlotList from './components/ParkingSlotList';
import VehicleList from './components/VehicleList';
import VehicleForm from './components/VehicleForm';
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
    return (
        <div style={{ padding: "20px" }}>
            <h1>Vehicle Parking Management</h1>
            <VehicleForm />
            <VehicleList />
            <ParkingSlotList />
        </div>
    );
}

export default App;
