import axios from 'axios';

// const API_BASE = 'http://3.85.122.237:8080/api/parking';
const API_BASE = 'http://localhost:8080/api/parking';

export const getSlots = () => axios.get(`${API_BASE}/slot-management/all`);

export const addVehicle = (vehicle) => axios.post(`${API_BASE}/vehicles/add`, vehicle);

export const unparkVehicle = (id) => axios.put(`${API_BASE}/vehicles/${id}/exit`);