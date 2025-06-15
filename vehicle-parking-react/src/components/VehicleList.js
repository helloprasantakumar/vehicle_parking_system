import React, {useEffect, useState} from 'react';
import {getVehicles, unparkVehicle} from '../services/ApiService';

function VehicleList() {
    const [vehicles, setVehicles] = useState([]);
    console.log(vehicles);

    useEffect(() => {
        fetchVehicles();
    }, []);

    const fetchVehicles = () => {
        getVehicles().then(res => setVehicles(res.data));
    };

    const handleUnpark = (id) => {
        unparkVehicle(id).then(() => fetchVehicles());
    };

    return (<div>
        <h2>Parked Vehicles</h2>
        <ul>
            {vehicles.map(vehicle => (<li key={vehicle.id}>
                {vehicle.licensePlate} ({vehicle.vehicleType}) - Slot: {vehicle.slot?.slotNumber}
                {vehicle.exitTime == null && (<button onClick={() => handleUnpark(vehicle.id)} style={{marginLeft: 10}}>
                    Unpark
                </button>)}
            </li>))}
        </ul>
    </div>);
}

export default VehicleList;
