import React, { useEffect, useState } from 'react';
import { getSlots } from '../services/ApiService';

function ParkingSlotList() {
    const [slots, setSlots] = useState([]);

    useEffect(() => {
        getSlots().then(res => setSlots(res.data));
    }, []);

    return (
        <div>
            <h2>Parking Slots</h2>
            <ul>
                {slots.map(slot => (
                    <li key={slot.id}>
                        Slot {slot.slotNumber} - {slot.occupied ? 'Occupied' : 'Free'}
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default ParkingSlotList;
