import React, { useEffect, useState } from 'react';
import VehicleForm from './VehicleForm';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function Dashboard({setIsLoggedIn }) {
    const [vehicles, setVehicles] = useState([]);
    const [slots, setSlots] = useState([]);
    const navigate = useNavigate();

    const fetchData = () => {
        axios.get('http://localhost:8080/api/parking/vehicles').then(res => setVehicles(res.data));
        axios.get('http://localhost:8080/api/parking/slots').then(res => setSlots(res.data));
    };

    useEffect(() => {
        fetchData();
    }, []);

    const handleUnpark = (id) => {
        axios.put(`http://localhost:8080/api/parking/vehicles/${id}/exit`).then(() => {
            alert('Vehicle unparked');
            fetchData();
        });
    };

    const handleLogout = () => {
        localStorage.removeItem('admin');
        setIsLoggedIn(false);
    };

    const admin = localStorage.getItem('admin');

    return (
        <div className="container mt-4">
            <div className="d-flex justify-content-between align-items-center mb-4">
                <h2 className="text-primary">Welcome, {admin}</h2>
                <button className="btn btn-outline-danger" onClick={handleLogout}>Logout</button>
            </div>
            <VehicleForm onPark={fetchData} />

            <div className="mt-5">
                <h4>All Parked Vehicles</h4>
                <table className="table table-bordered">
                    <thead className="table-light">
                    <tr>
                        <th>ID</th>
                        <th>License Plate</th>
                        <th>Type</th>
                        <th>User</th>
                        <th>Email</th>
                        <th>Mobile</th>
                        <th>Slot</th>
                        <th>Entry</th>
                        <th>Exit</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {vehicles.map(v => (
                        <tr key={v.id}>
                            <td>{v.id}</td>
                            <td>{v.licensePlate}</td>
                            <td>{v.vehicleType}</td>
                            <td>{v.userName}</td>
                            <td>{v.userEmail}</td>
                            <td>{v.userMobile}</td>
                            <td>{v.slot?.slotNumber}</td>
                            <td>{v.entryTime}</td>
                            <td>{v.exitTime || '-'}</td>
                            <td>
                                {!v.exitTime && (
                                    <button className="btn btn-danger btn-sm" onClick={() => handleUnpark(v.id)}>
                                        Unpark
                                    </button>
                                )}
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>

            <div className="mt-5">
                <h4>Parking Slot Information</h4>
                <table className="table table-striped">
                    <thead className="table-light">
                    <tr>
                        <th>ID</th>
                        <th>Slot Number</th>
                        <th>Status</th>
                    </tr>
                    </thead>
                    <tbody>
                    {slots.map(s => (
                        <tr key={s.id}>
                            <td>{s.id}</td>
                            <td>{s.slotNumber}</td>
                            <td>{s.occupied ? 'Occupied' : 'Available'}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default Dashboard;