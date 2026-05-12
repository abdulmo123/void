export type RegisterRequest = {
    firstName: string;
    lastName: string;
    username: string;
    email: string;
    password: string;
};

export type AuthResponse = {
    id: number;
    username: string;
    token: string;
};

export type LoginRequest = {
    username: string;
    password: string;
};