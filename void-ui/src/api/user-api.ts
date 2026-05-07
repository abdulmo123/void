import type { AuthResponse, LoginRequest, RegisterRequest } from "../types/user-type";

const VOID_USER_BACKEND_URL = 'http://localhost:8081';

export async function signUp(registerRequestData: RegisterRequest): Promise<AuthResponse> {
    const response = await fetch(`${VOID_USER_BACKEND_URL}/api/v1/auth/register`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(registerRequestData),
    });

    if (!response.ok) {
        throw new Error("Signup failed");
    }

    return response.json();
}

export async function login(loginRequestData: LoginRequest): Promise<AuthResponse> {
    const response = await fetch(`${VOID_USER_BACKEND_URL}/api/v1/auth/login`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(loginRequestData),
    });

    if (!response.ok) {
        throw new Error("Login failed");
    }

    return response.json();
}