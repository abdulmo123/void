import type { AuthResponse, RegisterRequest } from "./types";

const VOID_USER_BACKEND_URL = 'http://localhost:8081';
const VOID_POST_BACKEND_URL = 'http://localhost:8082';

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