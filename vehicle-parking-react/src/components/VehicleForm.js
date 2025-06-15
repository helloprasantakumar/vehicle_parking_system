import React, {useEffect, useState} from 'react';
import {addVehicle, getSlots} from '../services/ApiService';

function VehicleForm() {
    const [licensePlate, setNumber] = useState('');
    const [vehicleType, setType] = useState('');
    const [slotId, setSlotId] = useState('');
    const [slots, setSlots] = useState([]);

    useEffect(() => {
        getSlots().then(res => setSlots(res.data.filter(s => !s.occupied)));
    }, []);

    const handleSubmit = (e) => {
        e.preventDefault();
        const selectedSlot = slots.find(s => s.id === parseInt(slotId));
        addVehicle({
            licensePlate, vehicleType, slot: selectedSlot
        }).then(() => {
            setNumber('');
            setType('');
            setSlotId('');
            window.location.reload(); // optional: you can update state instead
        });
    };

    return (<div className="container mt-4">
            <div className="card shadow-sm">
                <div className="card-header bg-primary text-white">
                    <h4 className="mb-0">Park New Vehicle</h4>
                </div>
                <div className="card-body">
                    <form onSubmit={handleSubmit}>
                        <div className="mb-3">
                            <label className="form-label">Vehicle Number</label>
                            <input
                                type="text"
                                className="form-control"
                                value={licensePlate}
                                onChange={e => setNumber(e.target.value)}
                                placeholder="Enter vehicle number"
                                required
                            />
                        </div>
                        <div className="mb-3">
                            <label className="form-label">Vehicle Type</label>
                            <input
                                type="text"
                                className="form-control"
                                value={vehicleType}
                                onChange={e => setType(e.target.value)}
                                placeholder="Enter vehicle type"
                                required
                            />
                        </div>
                        <div className="mb-3">
                            <label className="form-label">Select Parking Slot</label>
                            <select
                                className="form-select"
                                value={slotId}
                                onChange={e => setSlotId(e.target.value)}
                                required
                            >
                                <option value="">-- Select Slot --</option>
                                {slots.map(s => (<option key={s.id} value={s.id}>{s.slotNumber}</option>))}
                            </select>
                        </div>
                        <button type="submit" className="btn btn-success w-100">Park Vehicle</button>
                    </form>
                </div>
            </div>
        </div>);
}

export default VehicleForm;
