import axios from 'axios';

const API_BASE = 'http://localhost:8080/api/parking';

export const getSlots = () => axios.get(`${API_BASE}/slots`);
export const getVehicles = () => axios.get(`${API_BASE}/vehicles`);
export const addVehicle = (vehicle) => axios.post(`${API_BASE}/vehicle`, vehicle);
export const unparkVehicle = (id) => axios.put(`${API_BASE}/vehicle/${id}/exit`);
