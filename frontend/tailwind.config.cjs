import { colors } from './src/lib/shared/utils/color';
import { boxShadows } from './src/lib/shared/utils/ui/shadow';
import breakpoints from './src/lib/shared/utils/breakpoint';

/** @type {import('tailwindcss').Config}*/
const config = {
    mode: 'jit',
    content: ['./src/**/*.{html,js,svelte,ts}'],
    theme: {
        fontFamily: {
            sans: ['Geist', 'sans-serif']
        },
        screens: {
            '3xs': `${breakpoints['3xs']}px`,
            '2xs': `${breakpoints['2xs']}px`,
            'xs': `${breakpoints['xs']}px`,
            'sm': `${breakpoints['sm']}px`,
            'md': `${breakpoints['md']}px`,
            'lg': `${breakpoints['lg']}px`,
            'xl': `${breakpoints['xl']}px`,
            '2xl': `${breakpoints['2xl']}px`,
            '3xl': `${breakpoints['3xl']}px`,
            '4xl': `${breakpoints['4xl']}px`,
            '5xl': `${breakpoints['5xl']}px`
        },
        extend: {
            colors,
            backgroundImage: {
                philosophy:
                    'radial-gradient(50% 50% at 50% 50%, rgba(240, 243, 255, 0.20) 0%, rgba(255, 255, 255, 0.00) 100%), linear-gradient(327deg, #f0f3ff 1.1%, #F0F3FF 97.1%)',
                sleep: 'radial-gradient(50% 50% at 50% 50%, rgba(240, 243, 255, 0.20) 0%, rgba(255, 255, 255, 0.00) 100%), linear-gradient(327deg, #BAE089 1.1%, #F0F3FF 97.1%)',
                insuline:
                    'radial-gradient(50% 50% at 50% 50%, rgba(240, 243, 255, 0.20) 0%, rgba(255, 255, 255, 0.00) 100%), linear-gradient(327deg, #99D62F 1.1%, #BAE089 97.1%)',
                cortisol:
                    'radial-gradient(50% 50% at 50% 50%, rgba(240, 243, 255, 0.20) 0%, rgba(255, 255, 255, 0.00) 100%), linear-gradient(327deg, #9CDDFF 1.1%, #F0F3FF 97.1%)',
                microbiome:
                    'radial-gradient(50% 50% at 50% 50%, rgba(240, 243, 255, 0.20) 0%, rgba(255, 255, 255, 0.00) 100%), linear-gradient(327deg, #4DB3FF 1.1%, #9CDDFF 97.1%)',
                activity:
                    'radial-gradient(50% 50% at 50% 50%, rgba(240, 243, 255, 0.20) 0%, rgba(255, 255, 255, 0.00) 100%), linear-gradient(327deg, #0E62BE 1.1%, #4DB3FF 97.1%)',
                intoxication:
                    'radial-gradient(50% 50% at 50% 50%, rgba(240, 243, 255, 0.20) 0%, rgba(255, 255, 255, 0.00) 100%), linear-gradient(327deg, #26A749 1.1%, #99D62F 97.1%)'
            },
            fontFamily: {
                'primary-regular': ['Nohemi-Regular', 'sans-serif'],
                'primary-medium': ['Nohemi-Medium', 'sans-serif'],
                'primary-semibold': ['Nohemi-SemiBold', 'sans-serif'],
                'primary-bold': ['Nohemi-Bold', 'sans-serif'],
                'secondary-light': ['Geist-light', 'sans-serif'],
                'secondary-regular': ['Geist-regular', 'sans-serif'],
                'secondary-medium': ['Geist-medium', 'sans-serif'],
                'secondary-semibold': ['Geist-semibold', 'sans-serif'],
                'secondary-bold': ['Geist-bold', 'sans-serif']
            },
            height: {
                120: '30rem',
                132: '33rem',
                144: '36rem',
                156: '38rem',
                168: '42rem',
                192: '48rem',
                216: '54rem',
                240: '60rem',
                260: '65rem',
                300: '75rem',
                320: '80rem',
                340: '85rem',
                360: '90rem',
                640: '100rem'
            },
            borderRadius: {
                '4xl': '32px',
                '5xl': '40px',
                '6xl': '48px',
                '7xl': '56px'
            },
            boxShadow: {
                ...boxShadows
            },
            keyframes: {
                pulseInnerShadow: {
                    '0%, 100%': {
                        boxShadow: 'inset 0 0 36px 4px #4FCF9A',
                    },
                    '50%': {
                        boxShadow: 'inset 0 0 36px 4px #FFD83E',
                    },
                },
                slideInRight: {
                    '0%': { transform: 'translateX(100%)', opacity: '0' },
                    '100%': { transform: 'translateX(0)', opacity: '1' },
                },
            },
            animation: {
                pulseInnerShadow: 'pulseInnerShadow 2s infinite',
                slideInRight: 'slideInRight 500ms ease-out',
            },
        }
    },
    plugins: [require('@tailwindcss/forms')],
    darkMode: 'class',
    safelist: [
        {
            pattern: /(bg|text|border)-(alpha|gray|parch|red|orange|yellow|green|teal|azure|blue|violet|purple|magenta)(01|02|03|04|05|06|07|08|09|10|11|12|13|14|15|16)/
        }
    ]
};

module.exports = config;
