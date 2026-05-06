import '@mantine/core/styles.css';
import { MantineProvider, localStorageColorSchemeManager } from '@mantine/core';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { theme } from './theme';
import Navbar from './components/Navbar';
import SignupPage from './pages/SignupPage';
import LoginPage from './pages/LoginPage';

const colorSchemeManager = localStorageColorSchemeManager({ key: 'void-color-scheme' });

export default function App() {
  return (
    <MantineProvider theme={theme} colorSchemeManager={colorSchemeManager}>
      <BrowserRouter>
        <Navbar />
        <Routes>
          <Route path="/" element={<LoginPage />}></Route>
          <Route path="/signup" element={<SignupPage />}></Route>
        </Routes>
      </BrowserRouter>
    </MantineProvider>
  );
}