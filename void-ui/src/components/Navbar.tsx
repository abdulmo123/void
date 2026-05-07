import { Group, Button, Text, ActionIcon } from '@mantine/core';
import { IconSun, IconMoon } from '@tabler/icons-react';
import { useNavigate } from 'react-router-dom';
import { useMantineColorScheme } from '@mantine/core';

export default function Navbar() {
    const { colorScheme, toggleColorScheme } = useMantineColorScheme();
    const navigate = useNavigate();
    const dark = colorScheme === 'dark';

    return (
        <header style={{
            borderBottom: `1px solid ${dark ? '#222222' : '#dddcda'}`,
            backgroundColor: dark ? '#0a0a0a' : '#f5f4f0',
            padding: '0 2rem',
            height: '60px',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'space-between',
        }}>

        {/* Logo */}
        <Text
            style={{
            fontFamily: 'Georgia, serif',
            fontSize: '22px',
            color: dark ? '#f5f4f0' : '#0a0a0a',
            cursor: 'pointer',
            }}
            onClick={() => navigate('/')}
        >
            void<span style={{ color: '#888888' }}>.</span>
        </Text>

      {/* Right side */}
        <Group gap="md">
            <ActionIcon
            variant="subtle"
            onClick={() => toggleColorScheme()}
            color={dark ? 'yellow' : 'dark'}
            >
            {dark ? <IconSun size={18} /> : <IconMoon size={18} />}
            </ActionIcon>

            <Button
                variant="subtle"
                size="xs"
                color={dark ? 'gray' : 'dark'}
                onClick={() => navigate('/login')}
                style={{ fontFamily: 'Courier New, monospace', letterSpacing: '1px', fontSize: '11px' }}
            >
                LOGIN
            </Button>

            <Button
                variant="filled"
                size="xs"
                color="dark"
                onClick={() => navigate('/signup')}
                style={{ fontFamily: 'Courier New, monospace', letterSpacing: '1px', fontSize: '11px' }}
            >
                SIGN UP
            </Button>
      </Group>
    </header>
    )
}