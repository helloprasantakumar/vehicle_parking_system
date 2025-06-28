import React, { useState } from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';

function AdminLogin() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/api/admin/login', {
                username,
                password
            });

            if (response.data === true) {
                localStorage.setItem('admin', username);
                window.location.href = '/dashboard';
            } else {
                setError("Invalid credentials");
            }
        } catch (err) {
            console.error(err);
            setError("Login failed");
        }
    };

    return (
        <div className="container mt-5">
            <div className="card p-4 shadow-lg col-md-6 mx-auto">
                <h3 className="mb-3">Admin Login</h3>
                <form onSubmit={handleLogin}>
                    <input className="form-control mb-2" placeholder="Username" value={username} onChange={(e) => setUsername(e.target.value)} required />
                    <input type="password" className="form-control mb-2" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} required />
                    {error && <div className="text-danger mb-2">{error}</div>}
                    <button className="btn btn-primary w-100">Login</button>
                </form>
            </div>
        </div>
    );
}

export default AdminLogin;