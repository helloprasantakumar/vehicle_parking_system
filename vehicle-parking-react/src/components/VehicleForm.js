import React, { useEffect, useState } from 'react';
import { addVehicle, getSlots } from '../services/ApiService';

function VehicleForm({ onPark }) {
    const [number, setNumber] = useState('');
    const [type, setType] = useState('');
    const [slotId, setSlotId] = useState('');
    const [slots, setSlots] = useState([]);
    const [userName, setUserName] = useState('');
    const [userEmail, setUserEmail] = useState('');
    const [userMobile, setUserMobile] = useState('');

    useEffect(() => {
        getSlots().then(res => setSlots(res.data.filter(s => !s.occupied)));
    }, []);

    const handleSubmit = (e) => {
        e.preventDefault();
        const selectedSlot = slots.find(s => s.id === parseInt(slotId));
        addVehicle({
            licensePlate: number,
            vehicleType: type,
            slot: selectedSlot,
            userName,
            userEmail,
            userMobile
        }).then(() => {
            alert("Vehicle Parked Successfully!");
            setNumber('');
            setType('');
            setSlotId('');
            setUserName('');
            setUserEmail('');
            setUserMobile('');
            onPark();
        });
    };

    return (
        <div className="card shadow-lg p-4">
            <h4 className="mb-4">Park New Vehicle</h4>
            <form onSubmit={handleSubmit}>
                <div className="row mb-3">
                    <div className="col">
                        <input className="form-control" placeholder="Vehicle Number" value={number} onChange={e => setNumber(e.target.value)} required />
                    </div>
                    <div className="col">
                        <input className="form-control" placeholder="Vehicle Type" value={type} onChange={e => setType(e.target.value)} required />
                    </div>
                </div>
                <div className="row mb-3">
                    <div className="col">
                        <input className="form-control" placeholder="Name" value={userName} onChange={e => setUserName(e.target.value)} required />
                    </div>
                    <div className="col">
                        <input className="form-control" placeholder="Email" value={userEmail} onChange={e => setUserEmail(e.target.value)} required />
                    </div>
                    <div className="col">
                        <input className="form-control" placeholder="Mobile" value={userMobile} onChange={e => setUserMobile(e.target.value)} required />
                    </div>
                </div>
                <div className="mb-3">
                    <select className="form-select" value={slotId} onChange={e => setSlotId(e.target.value)} required>
                        <option value="">Select Parking Slot</option>
                        {slots.map(s => (
                            <option key={s.id} value={s.id}>{s.slotNumber}</option>
                        ))}
                    </select>
                </div>
                <button type="submit" className="btn btn-primary text-center">Park Vehicle</button>
            </form>
        </div>
    );
}

export default VehicleForm;